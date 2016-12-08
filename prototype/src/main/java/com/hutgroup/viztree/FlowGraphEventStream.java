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
    MessageConsumer consumer;
    FlowGraph graph;
    Map<String, FlowGraphEdge> edgeMap;
    Map<String, Tuple<String, String>> orderTracker;
    Map<String, String> forwardingMap;

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

        for (int i = 0; i <= 3; i++) {
            try {
                Message msg = consumer.receive();
                if (!(msg instanceof TextMessage)) {
                    throw new RuntimeException("Expected a TextMessage");
                }
                message = ((TextMessage) msg).getText();

            } catch (Exception e) {
                    System.err.println(e);
            }
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


    List<String> unmarshallOrderManagerEdgeEvent(String inp)
     {

         String orderTypeRegex = "eventType[ ]*=[ ]*\\\"([^\\\"]*)\\\"";
         Pattern orderTypePattern = Pattern.compile(orderTypeRegex);
         Matcher orderTypeMatcher = orderTypePattern.matcher(inp);


         String orderNumberRegex = "<orderNumber>([^<]*)";
         Pattern orderNumberPattern = Pattern.compile(orderNumberRegex);
         Matcher orderNumberMatcher = orderNumberPattern.matcher(inp);


        String caseAnalysis = "", orderNumber = "";

         if(orderNumberMatcher.find() && orderTypeMatcher.find()){
             caseAnalysis = orderTypeMatcher.group(1);
             orderNumber = orderNumberMatcher.group(1);

         }


        String newStateTarget = forwardingMap.get(caseAnalysis);

         if(newStateTarget == null){
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
