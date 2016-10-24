package com.hutgroup.viztree;

import java.util.stream.*;
import java.util.function.*;
import com.hutgroup.viztree.graph.FlowGraphListener;
import org.jgrapht.graph.FlowGraphEdge;
import org.jgrapht.graph.FlowGraphNode;
import org.jgrapht.graph.FlowGraph;

public class App 
{
    public static void main( String[] args )
    {

	FlowGraph graph = initFlowGraph();
	FlowGraphUpdate initialUpdate = null;

	Stream.iterate(initialUpdate, prev -> nextUpdate()).forEach((graphUpdate) -> update(graph, graphUpdate));
	
    }


    private static void update(FlowGraph g, FlowGraphUpdate update)
    {
	throw new RuntimeException("Unimplemented everything");
    }	
    private static FlowGraph initFlowGraph()
    {
	FlowGraph g = new FlowGraph();
	g.addGraphListener(new FlowGraphListener());
	FlowGraphNode v1 = new FlowGraphNode("eq");
	FlowGraphNode v2 = new FlowGraphNode("ex");
	FlowGraphNode v3 = new FlowGraphNode("a");
	FlowGraphNode v4 = new FlowGraphNode("r");
	FlowGraphNode v5 = new FlowGraphNode("l");
	g.addVertex(v1);
	g.addVertex(v2);
	g.addVertex(v3);
	g.addVertex(v4);
	g.addVertex(v5);
	FlowGraphEdge e1 = g.addEdge(v1, v3);
	g.setEdgeWeight(e1, 2);
	FlowGraphEdge e2 = g.addEdge(v4, v3);
	g.setEdgeWeight(e2, 5);
	FlowGraphEdge e3 = g.addEdge(v4, v2);
	g.setEdgeWeight(e3,10);
	FlowGraphEdge e4 = g.addEdge(v4, v5);
	g.setEdgeWeight(e4, 6);
	FlowGraphEdge e5 = g.addEdge(v2, v3);
	g.setEdgeWeight(e5, 8);
	FlowGraphEdge e6 = g.addEdge(v2, v5);
	g.setEdgeWeight(e6, 5);
	FlowGraphEdge e7 = g.addEdge(v5, v2);
	g.setEdgeWeight(e7, 9);
	FlowGraphEdge e8 = g.addEdge(v5, v3);
	g.setEdgeWeight(e8, 7);
	FlowGraphEdge e9 = g.addEdge(v3, v4);
	g.setEdgeWeight(e9, 12);
	FlowGraphEdge e10 = g.addEdge(v3, v2);
	g.setEdgeWeight(e10, 6);
	FlowGraphEdge e11 = g.addEdge(v3, v5);
	g.setEdgeWeight(e11, 3);

	return g;
	
    }

    private static FlowGraphUpdate nextUpdate()
    {
	throw new UnsupportedOperationException("Unimplemented");
    }

    private static String[] getFlowGraphArgs(String []args)
    {
	throw new UnsupportedOperationException("Unimplemented");
    }
    private static String[] getListenerArgs(String []args)
    {
	throw new UnsupportedOperationException("Unimplemented");
    }
    private static String[] getUpdateStreamArgs(String []args)
    {
    throw new UnsupportedOperationException("Unimplemented");
    }

}
