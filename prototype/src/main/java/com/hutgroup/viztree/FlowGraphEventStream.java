package com.hutgroup.viztree;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import javax.jms.Session;

import javax.xml.bind.*;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;
import java.util.function.*;
import java.util.*;

import java.io.StringReader;

import com.hutgroup.viztree.graph.FlowGraphListener;
import org.jgrapht.event.FlowGraphEdgeChangeEvent;
import org.jgrapht.graph.FlowGraphEdge;
import org.jgrapht.graph.FlowGraphNode;
import org.jgrapht.graph.FlowGraph;
import org.jgrapht.Graph;

import com.hutgroup.viztree.orderevents.*;

import com.josh.utils.Tuple;
import com.josh.utils.StringUtils;

public class FlowGraphEventStream {

    public static final int EVENT_DELAY = 200;
    public static final String DEFAULT_STATE = "1";
    public static final int RECEIVE_TIME_OUT = 1000;
    MessageConsumer consumer;
    FlowGraph graph;
    Map<String, FlowGraphEdge> edgeMap;
    Map<String, Tuple<String, String>> orderTracker;
    Map<String, String> forwardingMap;

    private static final String ORDER_NUMBER_REGEX = "<orderNumber>([^<]*)";
    private static final Pattern ORDER_NUMBER_PATTERN= Pattern.compile(ORDER_NUMBER_REGEX);

    private static final String ORDER_TYPE_REGEX = "eventType[ ]*=[ ]*\"([^\"]*)\"";
    private static final Pattern ORDER_TYPE_PATTERN = Pattern.compile(ORDER_TYPE_REGEX);

    // TODO: Need to change this to the actual regex that finds the link inside the order body
    private static final String ORDER_LINK_REGEX = "<link rel=\"order\" href=\"([^\"]*)\"";
    private static final Pattern ORDER_LINK_PATTERN = Pattern.compile(ORDER_LINK_REGEX );

    // TODO: Need to change this to the actual regex that finds the order number inside the link
    private static final String ORDER_NUMBER_FROM_LINK_REGEX = "/([^/]*)$";
    private static final Pattern ORDER_NUMBER_FROM_LINK_PATTERN = Pattern.compile(ORDER_NUMBER_FROM_LINK_REGEX );


    public FlowGraphEventStream(MessageConsumer consumer, FlowGraph graph, Map<String, String> forwardingMap) {
        edgeMap = new HashMap<>();
        orderTracker = new HashMap<>();
        this.forwardingMap = forwardingMap;
        this.consumer = consumer;
        this.graph = graph;

    }

    public Stream<List<FlowGraphEdgeChangeEvent>> stream() {
        return Stream.iterate(nextUpdate(), prev -> nextUpdate());
    }

    /**
     * Pulls the next update from the MessageConsumer stream and parses it into
     * a flow graph edge change event.
     */

    private List<FlowGraphEdgeChangeEvent> nextUpdate() {

        String message = null;

        try {
            Message msg = consumer.receive(RECEIVE_TIME_OUT);
            if (!(msg instanceof TextMessage)) {
                return null;
            }

            message = ((TextMessage) msg).getText();

        } catch (Exception e) {
            return null;
        }


        return deserializeFlowGraphEdgeChangeEvent(graph, message);

    }


    List<FlowGraphEdgeChangeEvent> deserializeFlowGraphEdgeChangeEvent(Graph g, String msg) {

        System.out.println(msg);

        List<String> parts = unmarshallOrderManagerEdgeEvent(msg);
        String edgeSourceString = parts.get(0);
        String edgeMidTargetString = parts.get(1);
        String edgeMidSourceString = parts.get(1);
        String edgeTargetString = parts.get(2);

        System.out.println(parts);

        FlowGraphNode edgeSource = new FlowGraphNode(edgeSourceString);
        FlowGraphNode edgeMidTarget = new FlowGraphNode(edgeMidTargetString);
        FlowGraphNode edgeMidSource = new FlowGraphNode(edgeMidSourceString);
        FlowGraphNode edgeTarget = new FlowGraphNode(edgeTargetString);

        FlowGraphEdge e = edgeMap.get(edgeSourceString + "|" + edgeMidTargetString);
        FlowGraphEdge e2 = edgeMap.get(edgeMidSourceString + "|" + edgeTargetString);

        if (e == null) {
            System.out.println("Could not find message: " + edgeSource + " - " + edgeMidTarget);
            return null;
        }

        double oldWeight1 = graph.getEdgeWeight(e);

        double oldWeight2 = graph.getEdgeWeight(e2);


        List<FlowGraphEdgeChangeEvent> l = new ArrayList<>();

        l.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e,
                edgeSource, edgeMidTarget, oldWeight1, oldWeight1 - 1));
        l.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e,
                edgeMidSource, edgeTarget, oldWeight2, oldWeight2 + 1));

        return l;

    }


    private String extractOrderType(String inp) {

        Matcher orderTypeMatcher = ORDER_TYPE_PATTERN.matcher(inp);
        orderTypeMatcher.find();
        return orderTypeMatcher.group(1);

    }

    private String extractOrderNumberFromLink(String inp){

        Matcher orderLinkMatcher = ORDER_LINK_PATTERN.matcher(inp);
        orderLinkMatcher.find();
        Matcher orderNumberFromLinkMatcher = ORDER_NUMBER_FROM_LINK_PATTERN.matcher(orderLinkMatcher.group(1));
        orderNumberFromLinkMatcher.find();

        return orderNumberFromLinkMatcher.group(1);

    }

    private String extractOrderNumber(String inp){

        Matcher orderNumberMatcher = ORDER_NUMBER_PATTERN.matcher(inp);

        if(!orderNumberMatcher.find()){
            return extractOrderNumberFromLink(inp);
        }
        else {
            return orderNumberMatcher.group(1);
        }
    }

    List<String> unmarshallOrderManagerEdgeEvent(String inp) {


        String caseAnalysis = extractOrderType(inp);
        String orderNumber = extractOrderNumber(inp);

        String newStateTarget = forwardingMap.get(caseAnalysis);

        if (newStateTarget == null) {
            newStateTarget = DEFAULT_STATE;
        }


        Tuple<String, String> p = orderTracker.get(orderNumber);

        if (p == null) {
            p = new Tuple<>("1", "1");
            orderTracker.put(orderNumber, p);
        }

        orderTracker.put(orderNumber, new Tuple<>(p._2, newStateTarget));

        List<String> result = new LinkedList<String>();

        result.add(p._1);
        result.add(p._2);
        result.add(p._2);
        result.add(newStateTarget);

        return result;

    }




}
