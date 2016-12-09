
package com.hutgroup.viztree.graph;


import org.jgrapht.event.FlowGraphEdgeChangeEvent;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.event.IFlowGraphListener;
import org.jgrapht.graph.FlowGraphEdge;
import org.jgrapht.graph.FlowGraphNode;

/**
 * Created by CoutinhoJ on 09/12/2016.
 */
public class MockFlowGraphListener implements IFlowGraphListener<FlowGraphNode, FlowGraphEdge> {

        @Override
        public void vertexAdded(GraphVertexChangeEvent<FlowGraphNode> e) {}

        @Override
        public void vertexRemoved(GraphVertexChangeEvent<FlowGraphNode> e) {}

        @Override
        public void edgeAdded(GraphEdgeChangeEvent e) {}

        @Override
        public void edgeRemoved(GraphEdgeChangeEvent e) {}

        @Override
        public void edgeWeightChange(FlowGraphEdgeChangeEvent e) {}




}
