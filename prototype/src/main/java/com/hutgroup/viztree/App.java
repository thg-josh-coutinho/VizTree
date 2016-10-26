package com.hutgroup.viztree;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import com.hutgroup.viztree.graph.FlowGraphListener;
import org.jgrapht.event.FlowGraphEdgeChangeEvent;
import org.jgrapht.graph.FlowGraphEdge;
import org.jgrapht.graph.FlowGraphNode;
import org.jgrapht.graph.FlowGraph;

public class App 
{
    static int counter = 0;
    static LinkedList<FlowGraphEdgeChangeEvent> updateQueue;
    static FlowGraph graph;
    public static void main( String[] args )
    {
	Scanner sc = new Scanner(System.in);

	init();
	sc.next();

	Stream
	    .iterate(nextUpdate(), prev -> nextUpdate())
	    .forEach((graphUpdate) -> update(graph, graphUpdate));
	
    }


    private static void update(FlowGraph g, FlowGraphEdgeChangeEvent update)
    {
	g.setEdgeWeight(update.getEdge(), update.getNewWeight());
	try {
	    Thread.sleep((int)(Math.random()*2000));
	}
	catch(Exception e) {
	    System.err.println("Err occurred when pausing within update inside App.java");
	    System.err.println(e.toString());
	}
    }	

    private static void init()
    {
	graph = new FlowGraph();
	graph.addGraphListener(new FlowGraphListener());
	FlowGraphNode v1 = new FlowGraphNode("eq");
	FlowGraphNode v2 = new FlowGraphNode("ex");
	FlowGraphNode v3 = new FlowGraphNode("a");
	FlowGraphNode v4 = new FlowGraphNode("r");
	FlowGraphNode v5 = new FlowGraphNode("l");
	graph.addVertex(v1);
	graph.addVertex(v2);
	graph.addVertex(v3);
	graph.addVertex(v4);
	graph.addVertex(v5);
	FlowGraphEdge e1 = graph.addEdge(v1, v3);
	graph.setEdgeWeight(e1, 2);
	FlowGraphEdge e2 = graph.addEdge(v4, v3);
	graph.setEdgeWeight(e2, 5);
	FlowGraphEdge e3 = graph.addEdge(v4, v2);
	graph.setEdgeWeight(e3,10);
	FlowGraphEdge e4 = graph.addEdge(v4, v5);
	graph.setEdgeWeight(e4, 6);
	FlowGraphEdge e5 = graph.addEdge(v2, v3);
	graph.setEdgeWeight(e5, 8);
	FlowGraphEdge e6 = graph.addEdge(v2, v5);
	graph.setEdgeWeight(e6, 5);
	FlowGraphEdge e7 = graph.addEdge(v5, v2);
	graph.setEdgeWeight(e7, 9);
	FlowGraphEdge e8 = graph.addEdge(v5, v3);
	graph.setEdgeWeight(e8, 7);
	FlowGraphEdge e9 = graph.addEdge(v3, v4);
	graph.setEdgeWeight(e9, 12);
	FlowGraphEdge e10 = graph.addEdge(v3, v2);
	graph.setEdgeWeight(e10, 6);
	FlowGraphEdge e11 = graph.addEdge(v3, v5);
	graph.setEdgeWeight(e11, 3);


	FlowGraphNode edgeSource1 = v1;
	FlowGraphNode edgeTarget1 = v3;
	double oldWeight1 = 2;
	double newWeight1 = 10;

	FlowGraphNode edgeSource2 = v4;
	FlowGraphNode edgeTarget2 = v3;
	double oldWeight2 = 5;
	double newWeight2 = 12;

	FlowGraphNode edgeSource3 = v4;
	FlowGraphNode edgeTarget3 = v2;
	double oldWeight3 = 10;
	double newWeight3 = 2;

	FlowGraphNode edgeSource4 = v4;
	FlowGraphNode edgeTarget4 = v5;
	double oldWeight4 = 6;
	double newWeight4 = 12;

	FlowGraphNode edgeSource5 = v2;
	FlowGraphNode edgeTarget5 = v3;
	double oldWeight5 = 8;
	double newWeight5 = 4;

	updateQueue = new LinkedList<>();

	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e1,
						     edgeSource1, edgeTarget1, oldWeight1, newWeight1));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e2,
						     edgeSource2, edgeTarget2, oldWeight2, newWeight2));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e3,
						     edgeSource3, edgeTarget3, oldWeight3, newWeight3));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e4,
						     edgeSource4, edgeTarget4, oldWeight4, newWeight4));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e5,
						     edgeSource5, edgeTarget5, oldWeight5, newWeight5));
	
    }

    private static FlowGraphEdgeChangeEvent nextUpdate()
    {
	
	counter = (counter+1)%updateQueue.size();
	
	FlowGraphEdgeChangeEvent e = updateQueue.get(counter);
	e.setNewWeight(Math.random()*30);
	return e;
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
