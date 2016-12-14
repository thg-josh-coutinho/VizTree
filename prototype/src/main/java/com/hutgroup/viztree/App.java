package com.hutgroup.viztree;

import javax.xml.bind.*;


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
    private List<FlowGraphEdgeChangeEvent> messages;

    public App(Stream<List<FlowGraphEdgeChangeEvent>> inputStream, FlowGraph graph) {
        this.edgeEventStream = inputStream;
        this.graph = graph;
    }


    /**
     * Updates the graph with each 'graphUpdate' pulled from the
     * MessageConsumer stream.
     */
    public void runApp(int numberOfMessages) throws Exception // Remove this throws exception
    {
        if (numberOfMessages >= 0) {
            edgeEventStream.limit(numberOfMessages).forEach((graphUpdate) -> {
                for (FlowGraphEdgeChangeEvent upd : graphUpdate) {
                    update(graph, upd);
                }
            });
        } else {
            edgeEventStream.forEach((graphUpdate) -> {
                for (FlowGraphEdgeChangeEvent upd : graphUpdate) {
                    update(graph, upd);
                }
            });
        }
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


        // remove this eventually
        try {
            Thread.sleep(EVENT_DELAY);
        } catch (Exception e) {
            System.err.println("Err occurred when pausing within update inside App.java");
            e.printStackTrace();
        }

    }


}
