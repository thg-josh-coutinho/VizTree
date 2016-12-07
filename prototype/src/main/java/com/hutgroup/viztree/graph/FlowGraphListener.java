package com.hutgroup.viztree.graph;

import org.jgrapht.event.*;
import org.jgrapht.graph.*;
import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;
import com.hutgroup.viztree.FlowEventObject;

public class FlowGraphListener implements IFlowGraphListener<FlowGraphNode, FlowGraphEdge> {

    SocketIOServer server;

    public FlowGraphListener() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        System.out.printf("Starting server on: %s:%d\n", "localhost", 9092);
        server = new SocketIOServer(config);
        server.start();
    }

    @Override
    public void vertexAdded(GraphVertexChangeEvent<FlowGraphNode> e) {
    }

    @Override
    public void vertexRemoved(GraphVertexChangeEvent<FlowGraphNode> e) {
    }

    @Override
    public void edgeAdded(GraphEdgeChangeEvent e) {
        server.getBroadcastOperations().sendEvent("flowevent", e.toString());
    }

    @Override
    public void edgeRemoved(GraphEdgeChangeEvent e) {
        server.getBroadcastOperations().sendEvent("flowevent", e.toString());
    }

    @Override
    public void edgeWeightChange(FlowGraphEdgeChangeEvent e) {
        System.out.println("Broadcasting " + e);
        server.getBroadcastOperations().sendEvent("flowevent",
                new FlowEventObject("server",
                        e.getEdgeSource() + "-" + e.getEdgeTarget(),
                        e.getNewWeight()));
    }

}
