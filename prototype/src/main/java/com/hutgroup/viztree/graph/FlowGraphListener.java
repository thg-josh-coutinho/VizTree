package com.hutgroup.viztree.graph;

import org.jgrapht.event.*;
import org.jgrapht.graph.*;
import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;


public class FlowGraphListener implements GraphListener<FlowGraphNode, FlowGraphEdge> {

    SocketIOServer server;

    public FlowGraphListener(){
	Configuration config = new Configuration();
	config.setHostname("localhost");
	config.setPort(9092);

	server = new SocketIOServer(config);
	server.start();
    }

    @Override
    public void vertexAdded(GraphVertexChangeEvent<FlowGraphNode> e) {}

    @Override
    public void vertexRemoved(GraphVertexChangeEvent<FlowGraphNode> e) {}

    @Override
    public void edgeAdded(GraphEdgeChangeEvent e) {
	server.getBroadcastOperations().sendEvent("chatevent", e.toString());
    }

    @Override
    public void edgeRemoved(GraphEdgeChangeEvent e) {
	server.getBroadcastOperations().sendEvent("chatevent", e.toString());
    }

    public void edgeWeightChange(FlowGraphEdgeChangeEvent e) {
	server.getBroadcastOperations().sendEvent("chatevent", e.toString());
    }

}
