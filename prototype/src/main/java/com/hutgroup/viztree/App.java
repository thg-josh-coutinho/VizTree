package com.hutgroup.viztree;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.*;
import java.util.function.*;
import com.hutgroup.viztree.graph.FlowGraphListener;
import org.jgrapht.event.FlowGraphEdgeChangeEvent;
import org.jgrapht.graph.FlowGraphEdge;
import org.jgrapht.graph.FlowGraphNode;
import org.jgrapht.graph.FlowGraph;
import org.jgrapht.Graph;

public class App 
{
    static int counter = 0;
    static LinkedList<FlowGraphEdgeChangeEvent> updateQueue;
    static FlowGraph graph;
    static MessageConsumer consumer;
    public static void main( String[] args ) throws Exception
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

    private static void init() throws Exception
    {


	ConnectionFactory factory = 
	    new ActiveMQConnectionFactory(); 
	Connection con = factory.createConnection();
	try {
	    Session session = 
		con.createSession(false, Session.AUTO_ACKNOWLEDGE); 
	    consumer = session.createConsumer(session.createQueue("SampleQueue"));
	    con.start();                                            
	} catch (Exception e) { System.out.println("Failed to connect to ActiveMQ!\n" + e); System.exit(0); }


	graph = new FlowGraph();
	graph.addGraphListener(new FlowGraphListener());
	FlowGraphNode va  =  new FlowGraphNode("a");
	FlowGraphNode v1  =  new FlowGraphNode("1");
	FlowGraphNode v2  =  new FlowGraphNode("2");
	FlowGraphNode v3  =  new FlowGraphNode("3");
	FlowGraphNode v4  =  new FlowGraphNode("4");
	FlowGraphNode v5  =  new FlowGraphNode("5");
	FlowGraphNode v6  =  new FlowGraphNode("6");
	FlowGraphNode v7  =  new FlowGraphNode("7");
	FlowGraphNode v8  =  new FlowGraphNode("8");
	FlowGraphNode v9  = new FlowGraphNode("9");
	FlowGraphNode v10 = new FlowGraphNode("10");
	FlowGraphNode v11 = new FlowGraphNode("11");
	FlowGraphNode v12 = new FlowGraphNode("12");
	FlowGraphNode v13 = new FlowGraphNode("13");
	FlowGraphNode vl  = new FlowGraphNode("l");
	FlowGraphNode v14 = new FlowGraphNode("14");
	FlowGraphNode veq = new FlowGraphNode("eq");
	FlowGraphNode vr  = new FlowGraphNode("r");
	FlowGraphNode v21 = new FlowGraphNode("21");
	FlowGraphNode vex = new FlowGraphNode("ex");
	FlowGraphNode v22 = new FlowGraphNode("22");
	
	graph.addVertex(va );
	graph.addVertex(v1 );
	graph.addVertex(v2 );
	graph.addVertex(v3 );
	graph.addVertex(v4 );
	graph.addVertex(v5 );
	graph.addVertex(v6 );
	graph.addVertex(v7 );
	graph.addVertex(v8 );
	graph.addVertex(v9 );
	graph.addVertex(v10);
	graph.addVertex(v11);
	graph.addVertex(v12);
	graph.addVertex(v13);
	graph.addVertex(vl );
	graph.addVertex(v14);
	graph.addVertex(veq);
	graph.addVertex(vr );
	graph.addVertex(v21);
	graph.addVertex(vex);
	graph.addVertex(v22);


	FlowGraphEdge e1 = graph.addEdge (veq, v21);
	graph.setEdgeWeight(e1, 1);	            
	FlowGraphEdge e2 = graph.addEdge (v21, v1); 
	graph.setEdgeWeight(e2, 1);	            
	FlowGraphEdge e3 = graph.addEdge (v1, v2);  
	graph.setEdgeWeight(e3,10);	            
	FlowGraphEdge e4 = graph.addEdge (v2, v3);  
	graph.setEdgeWeight(e4, 1);	            
	FlowGraphEdge e5 = graph.addEdge (v3, v3);  
	graph.setEdgeWeight(e5, 1);	            
	FlowGraphEdge e6 = graph.addEdge (v3, v14); 
	graph.setEdgeWeight(e6, 1);	            
	FlowGraphEdge e7 = graph.addEdge (v14, v4); 
	graph.setEdgeWeight(e7, 1);	            
	FlowGraphEdge e8 = graph.addEdge (v4, v22); 
	graph.setEdgeWeight(e8, 1);	            
	FlowGraphEdge e9 = graph.addEdge (v22, v5); 
	graph.setEdgeWeight(e9, 1);	            
	FlowGraphEdge e10 = graph.addEdge(v5, v6);  
	graph.setEdgeWeight(e10, 1);	            
	FlowGraphEdge e11 = graph.addEdge(v6, v7);  
	graph.setEdgeWeight(e11, 1);	            
					            
	FlowGraphEdge e12 = graph.addEdge(v6, v8);  
	graph.setEdgeWeight(e12, 1);	            
					            
	FlowGraphEdge e13 = graph.addEdge(v2, v10); 
	graph.setEdgeWeight(e13, 1);	            
					            
	FlowGraphEdge e14 = graph.addEdge(v10, v11);
	graph.setEdgeWeight(e14, 1);	            
					            
	FlowGraphEdge e15 = graph.addEdge(v11, v12);
	graph.setEdgeWeight(e15, 1);	            
					            
	FlowGraphEdge e16 = graph.addEdge(v11, v13);
	graph.setEdgeWeight(e16, 1);	            
					            
	FlowGraphEdge e17 = graph.addEdge(v2, v9);  
	graph.setEdgeWeight(e17, 1);	            
					            
	FlowGraphEdge e18 = graph.addEdge(v1, v9);  
	graph.setEdgeWeight(e18, 1);	            
					            
	FlowGraphEdge e19 = graph.addEdge(v4, v9);  
	graph.setEdgeWeight(e19, 1);



	FlowGraphNode edgeSource1 = v1;
	FlowGraphNode edgeTarget1 = v3;
	double oldWeight1 = 1;
	double newWeight1 = 10;

	FlowGraphNode edgeSource2 = v4;
	FlowGraphNode edgeTarget2 = v3;
	double oldWeight2 = 1;
	double newWeight2 = 12;

	FlowGraphNode edgeSource3 = v4;
	FlowGraphNode edgeTarget3 = v2;
	double oldWeight3 = 1;
	double newWeight3 = 2;

	FlowGraphNode edgeSource4 = v4;
	FlowGraphNode edgeTarget4 = v5;
	double oldWeight4 = 1;
	double newWeight4 = 12;

	FlowGraphNode edgeSource5 = v2;
	FlowGraphNode edgeTarget5 = v3;
	double oldWeight5 = 1;
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
	System.out.println("Requesting a new update from the graph");
	for(int i = 0; i <= 5; i++){
	    try {

		Message msg = consumer.receive();
		if (! (msg instanceof TextMessage)) {
		    throw new RuntimeException("Expected a TextMessage");
		}
		TextMessage tm = (TextMessage) msg;
		System.out.println("Delivering: " + tm.getText());		
		return deserializeFlowGraphEdgeChangeEvent(graph, tm.getText());

	    } catch (Exception e) {
		try {
		    Thread.sleep((int)(1000*Math.pow(2, i)));
		} catch(Exception e2) {
		    
		}
	    }
	}

	System.err.println("Failed to retrieve an update 6 times, Quitting...");
	System.exit(0);
	return null;
	/*counter = (counter+1)%updateQueue.size();
	
	FlowGraphEdgeChangeEvent e = updateQueue.get(counter);
	e.setNewWeight(Math.random()*30);
	return e;*/
    }

    private static FlowGraphEdgeChangeEvent deserializeFlowGraphEdgeChangeEvent(Graph g, String msg)
    {

	String parts[] = msg.split("|");
	String edgeSourceString = parts[0];
	String edgeTargetString = parts[1];
	String newWeightString = parts[2];

	FlowGraphNode edgeSource = new FlowGraphNode(edgeSourceString);
	FlowGraphNode edgeTarget = new FlowGraphNode(edgeTargetString);
	FlowGraphEdge e = graph.getEdge(edgeSource, edgeTarget);
	double oldWeight = graph.getEdgeWeight(e);
	double newWeight = Double.parseDouble(newWeightString);


	return new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e,
					    edgeSource, edgeTarget, oldWeight, newWeight);
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
