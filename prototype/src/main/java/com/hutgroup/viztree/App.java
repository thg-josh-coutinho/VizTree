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

public class App
{
    static int counter = 0;
    static LinkedList<FlowGraphEdgeChangeEvent> updateQueue;
    static FlowGraph graph;
    static MessageConsumer consumer;
    static Map<String, FlowGraphEdge> edgeMap;
    static Unmarshaller jaxbUnmarshaller;
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
	if(update == null) { System.out.println("Null update"); return; }
	
	g.setEdgeWeight(update.getEdge(), update.getNewWeight());

	try {  Thread.sleep((int)(200)); }
	catch(Exception e) {
	    System.err.println("Err occurred when pausing within update inside App.java");
	    System.err.println(e.toString());
	}

    }

    private static void initActiveMQConsumer()
    {
	try{
	    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
 
	    Connection connection = connectionFactory.createConnection();
	    connection.start();

	    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    Destination destination = session.createQueue("SampleQueue");

	    consumer = session.createConsumer(destination);

	    Message message = consumer.receive();
 
	    if (message instanceof TextMessage) {
		TextMessage textMessage = (TextMessage) message;
		String text = textMessage.getText();
		System.out.println("Received: " + text);
	    }
	    else { System.out.println("Received: " + message); }
	}
	catch(Exception e) {
	    System.out.println("Could not connect to activemq");
	    System.exit(1);
	}

    }

    private static void initGraph()
    {
	graph = new FlowGraph();
	graph.addGraphListener(new FlowGraphListener());
	FlowGraphNode va  = new FlowGraphNode("a");
	FlowGraphNode v1  = new FlowGraphNode("1");
	FlowGraphNode v2  = new FlowGraphNode("2");
	FlowGraphNode v3  = new FlowGraphNode("3");
	FlowGraphNode v4  = new FlowGraphNode("4");
	FlowGraphNode v5  = new FlowGraphNode("5");
	FlowGraphNode v6  = new FlowGraphNode("6");
	FlowGraphNode v7  = new FlowGraphNode("7");
	FlowGraphNode v8  = new FlowGraphNode("8");
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

	edgeMap.put(("eq|21"), e1 );
	edgeMap.put(("21|1" ), e2 );  
	edgeMap.put(("1|2" ), e3 );
	edgeMap.put(("2|3" ), e4 );
	edgeMap.put(("3|3" ), e5 );
	edgeMap.put(("3|14"), e6 );
	edgeMap.put(("14|4" ), e7 );  
	edgeMap.put(("4|22"), e8 );
	edgeMap.put(("225" ), e9 ); 
	edgeMap.put(("5|6" ), e10);
	edgeMap.put(("6|7" ), e11);
	edgeMap.put(("6|8" ), e12);
	edgeMap.put(("2|10"), e13);
	edgeMap.put(("10|11"), e14);
	edgeMap.put(("11|12"), e15);
	edgeMap.put(("11|13"), e16);
	edgeMap.put(("2|9" ), e17);
	edgeMap.put(("9|1" ), e18);
	edgeMap.put(("4|9" ), e19);

	



	FlowGraphNode edgeSource1 = v1;
	FlowGraphNode edgeTarget1 = v2;
	double oldWeight1 = 1;
	double newWeight1 = 10;

	FlowGraphNode edgeSource2 = v2;
	FlowGraphNode edgeTarget2 = v3;
	double oldWeight2 = 1;
	double newWeight2 = 12;

	FlowGraphNode edgeSource3 = v3;
	FlowGraphNode edgeTarget3 = v14;
	double oldWeight3 = 1;
	double newWeight3 = 2;

	FlowGraphNode edgeSource4 = v14;
	FlowGraphNode edgeTarget4 = v4;
	double oldWeight4 = 1;
	double newWeight4 = 12;

	FlowGraphNode edgeSource5 = v5;
	FlowGraphNode edgeTarget5 = v6;
	double oldWeight5 = 1;
	double newWeight5 = 4;

	FlowGraphNode edgeSource6 = v6;
	FlowGraphNode edgeTarget6 = v7;
	double oldWeight6 = 1;
	double newWeight6 = 12;

	FlowGraphNode edgeSource7 = v6;
	FlowGraphNode edgeTarget7 = v8;
	double oldWeight7 = 1;
	double newWeight7 = 4;

	FlowGraphNode edgeSource8 = v2;
	FlowGraphNode edgeTarget8 = v10;
	double oldWeight8 = 1;
	double newWeight8 = 3;


	FlowGraphNode edgeSource9 = v10;
	FlowGraphNode edgeTarget9 = v11;
	double oldWeight9 = 1;
	double newWeight9 = 7;

	FlowGraphNode edgeSource10 = v11;
	FlowGraphNode edgeTarget10 = v12;
	double oldWeight10 = 1;
	double newWeight10 = 16;

	FlowGraphNode edgeSource11 = v11;
	FlowGraphNode edgeTarget11 = v13;
	double oldWeight11 = 1;
	double newWeight11 = 16;

	FlowGraphNode edgeSource12 = v2;
	FlowGraphNode edgeTarget12 = v9;
	double oldWeight12 = 1;
	double newWeight12 = 3;

	FlowGraphNode edgeSource13 = v9;
	FlowGraphNode edgeTarget13 = v1;
	double oldWeight13 = 1;
	double newWeight13 = 7;

	FlowGraphNode edgeSource14 = v4;
	FlowGraphNode edgeTarget14 = v9;
	double oldWeight14 = 1;
	double newWeight14 = 9;

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
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e6,
						     edgeSource6, edgeTarget6, oldWeight6, newWeight6));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e7,
						     edgeSource7, edgeTarget7, oldWeight7, newWeight7));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e8,
						     edgeSource8, edgeTarget8, oldWeight8, newWeight8));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e9,
						     edgeSource9, edgeTarget9, oldWeight9, newWeight9));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e10,
						     edgeSource10, edgeTarget10, oldWeight10, newWeight10));
	
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e11,
						     edgeSource11, edgeTarget11, oldWeight11, newWeight11));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e12,
						     edgeSource12, edgeTarget12, oldWeight12, newWeight12));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e13,
						     edgeSource13, edgeTarget13, oldWeight13, newWeight13));
	updateQueue.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e14,
						     edgeSource14, edgeTarget14, oldWeight14, newWeight14));

    }

    private static void initUnmarshaller()
    {
	String packageNames = "com.hutgroup.vitree.orderevents";
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(packageNames);  
	    jaxbUnmarshaller = jaxbContext.createUnmarshaller(); 
	} catch(Exception e)
	    {
		System.err.println("Could not create the unmarshaller, exiting");
		System.exit(1);
	    }
    }


    private static void init() throws Exception
    {
	edgeMap = new HashMap<>();

	initActiveMQConsumer();

	initGraph();

	initUnmarshaller();
	
    }

    private static FlowGraphEdgeChangeEvent nextUpdate()
    {
	System.out.println("Requesting a new update from the graph");
	String message = null;

	for(int i = 0; i <= 3; i++){
	    try {
		Message msg = consumer.receive();
		if (! (msg instanceof TextMessage)) {
		    throw new RuntimeException("Expected a TextMessage");
		}
		message = ((TextMessage) msg).getText();

	    } catch (Exception e) {
		try {
		    System.out.println(e);
		    Thread.sleep((int)(1000*Math.pow(2, i)));
		} catch(Exception e2) {
		    System.out.println(e2);
		}
	    }
	}

	System.out.println("Delivering: " + message);		
	return deserializeFlowGraphEdgeChangeEvent(graph, StringUtils::split, message);

    }

    private static FlowGraphEdgeChangeEvent deserializeFlowGraphEdgeChangeEvent(Graph g, Function<String, List<String>> deserializer, String msg)
    {
	System.out.println(msg);

	List<String> parts = deserializer.apply("\\|");
	String edgeSourceString = parts.get(0);
	String edgeTargetString = parts.get(1);
	String newWeightString = parts.get(2);
	System.out.println(parts);

	FlowGraphNode edgeSource = new FlowGraphNode(edgeSourceString);
	FlowGraphNode edgeTarget = new FlowGraphNode(edgeTargetString);

	FlowGraphEdge e = edgeMap.get(edgeSourceString + "|" + edgeTargetString);
	if(e == null) { System.out.println("Could not find message: " + edgeSource + " - " + edgeTarget); return null; }
	double oldWeight = graph.getEdgeWeight(e);
	double newWeight = Double.parseDouble(newWeightString);


	return new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e,
					    edgeSource, edgeTarget, oldWeight, newWeight);
    }


    private static List<String> unmarshallOrderManagerEdgeEvent(String inp)
    {
	try { 
	Object o = jaxbUnmarshaller.unmarshal(new StringReader(inp));
	} catch(Exception e) { System.out.println("Failed to parse " + inp); }
	throw new RuntimeException("Unimplemented!");
	
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
