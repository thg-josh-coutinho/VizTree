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

public class App {


    public static final int EVENT_DELAY = 200;
    private FlowGraph graph;
    private Stream<List<FlowGraphEdgeChangeEvent>> edgeEventStream;
    private Unmarshaller jaxbUnmarshaller;
    private List<FlowGraphEdgeChangeEvent> messages;

    public App(Stream<List<FlowGraphEdgeChangeEvent>> inputStream, FlowGraph graph, Unmarshaller unmarshaller) {
        this.edgeEventStream = inputStream;
        this.graph = graph;
        this.jaxbUnmarshaller = unmarshaller;
    }


    /**
     * Updates the graph with each 'graphUpdate' pulled from the
     * MessageConsumer stream.
     */
    public void runApp() throws Exception // Remove this throws exception
    {
        edgeEventStream.forEach((graphUpdate) -> {
            for (FlowGraphEdgeChangeEvent upd : graphUpdate) {
                update(graph, upd);
            }
        });
    }


    /**
     * Updates the edge with the new weight recieved from the event.
     * Then waits to space out updates.
     */
    private void update(FlowGraph g, FlowGraphEdgeChangeEvent update) {
        if (update == null) {
            System.err.println("Null update");
            return;
        }

        g.setEdgeWeight(update.getEdge(), update.getNewWeight());

        try {
            Thread.sleep(EVENT_DELAY);
        } catch (Exception e) {
            System.err.println("Err occurred when pausing within update inside App.java");
            e.printStackTrace();
        }

    }


}
