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
    public static final int EVENT_DELAY = 200;

    FlowGraph graph;
    MessageConsumer consumer;
    Map<String, FlowGraphEdge> edgeMap;
    Unmarshaller jaxbUnmarshaller;
    Map<String, Tuple<String, String>> orderTracker;

    public App(MessageConsumer consumer, FlowGraph graph, Unmarshaller unmarshaller)
    {
	edgeMap = new HashMap<>();
	orderTracker = new HashMap<>();

	this.consumer = consumer;
	this.graph = graph;
	this.unmarhsaller = unmarshaller;
    }


    public void runApp() throws Exception // Remove this throws exception
    {
	Stream
	    .iterate(nextUpdate(), prev -> nextUpdate())
	    .forEach((graphUpdate) -> {
		     for(FlowGraphEdgeChangeEvent upd : graphUpdate) {
			 update(graph, upd);
		     }
		});
    }


    private void update(FlowGraph g, FlowGraphEdgeChangeEvent update)
    {
	if(update == null) { System.err.println("Null update"); return; }
	
	g.setEdgeWeight(update.getEdge(), update.getNewWeight());

	try {  Thread.sleep(EVENT_DELAY); }
	catch(Exception e) {
	    System.err.println("Err occurred when pausing within update inside App.java");
	    System.err.println(e.toString());
	}

    }


    private List<FlowGraphEdgeChangeEvent> nextUpdate()
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

	return deserializeFlowGraphEdgeChangeEvent(graph,
						   App::unmarshallOrderManagerEdgeEvent,
 					           message);

    }

    private List<FlowGraphEdgeChangeEvent> deserializeFlowGraphEdgeChangeEvent(Graph g, Function<String, List<String>> deserializer, String msg)
    {

	System.out.println(msg);

	List<String> parts = deserializer.apply(msg);
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

	if(e == null) { System.out.println("Could not find message: " + edgeSource + " - " + edgeMidTarget); return null; }

	double oldWeight1 = graph.getEdgeWeight(e);

	double oldWeight2 = graph.getEdgeWeight(e2);


	List<FlowGraphEdgeChangeEvent> l = new ArrayList<>();

	l.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e,
					   edgeSource, edgeMidTarget, oldWeight1, oldWeight1-1));
	l.add(new FlowGraphEdgeChangeEvent(graph, FlowGraphEdgeChangeEvent.EDGE_WEIGHT_CHANGE, e,
					   edgeMidSource, edgeTarget, oldWeight2, oldWeight2+1));

	return l;

    }


    private List<String> unmarshallOrderManagerEdgeEvent(String inp)
    {

	Object o;

	try { 
	    o = jaxbUnmarshaller.unmarshal(new StringReader(inp));
	} catch(Exception e) { System.out.println("Failed to parse " + inp); return null; }

	Object caseAnalysis = ((JAXBElement)o).getValue();

	String newStateTarget;
	String orderId;

	// Same events correspond to different edges
	if ( caseAnalysis instanceof CancelOrderRequest           ) { orderId = ((CancelOrderRequest           ) caseAnalysis).getLink().getHref(); newStateTarget = "11"; }
	if ( caseAnalysis instanceof ChargeInvoiceRequest	  ) { orderId = ((ChargeInvoiceRequest	   ) caseAnalysis).getLink().getHref(); newStateTarget = "2";  }
	if ( caseAnalysis instanceof DespatchEvent		  ) { orderId = ((DespatchEvent		   ) caseAnalysis).getLink().getHref(); newStateTarget = "5";  }
	if ( caseAnalysis instanceof FraudCheckRequest		  ) { orderId = ((FraudCheckRequest		   ) caseAnalysis).getLink().getHref(); newStateTarget = "3";  }
	if ( caseAnalysis instanceof FraudStatusUpdate		  ) { orderId = ((FraudStatusUpdate		   ) caseAnalysis).getLink().getHref(); newStateTarget = "3";  }
	if ( caseAnalysis instanceof FulfilmentRequest		  ) { orderId = ((FulfilmentRequest		   ) caseAnalysis).getLink().getHref(); newStateTarget = "3";  }
	if ( caseAnalysis instanceof InvoiceFailureEvent	  ) { orderId = ((InvoiceFailureEvent	   ) caseAnalysis).getLink().getHref(); newStateTarget = "8";  }
	if ( caseAnalysis instanceof InvoiceRetryEvent		  ) { orderId = ((InvoiceRetryEvent		   ) caseAnalysis).getLink().getHref(); newStateTarget = "8";  }
	if ( caseAnalysis instanceof InvoiceSuccessEvent	  ) { orderId = ((InvoiceSuccessEvent	   ) caseAnalysis).getLink().getHref(); newStateTarget = "7";  }
	if ( caseAnalysis instanceof NewInvoiceRequest		  ) { orderId = ((NewInvoiceRequest		   ) caseAnalysis).getLink().getHref(); newStateTarget = "2";  }
	if ( caseAnalysis instanceof NewOrderRequest		  ) { orderId = ((NewOrderRequest		   ) caseAnalysis).
		getLink().
		getHref();
	    newStateTarget = "2";  }
	if ( caseAnalysis instanceof PayresolveRefulfilmentRequest) { orderId = ((PayresolveRefulfilmentRequest) caseAnalysis).getLink().getHref(); newStateTarget = "1";  }
	if ( caseAnalysis instanceof RefundOrderRequest		  ) { orderId = ((RefundOrderRequest	   ) caseAnalysis).getLink().getHref(); newStateTarget = "6";  }
	if ( caseAnalysis instanceof ReleaseRequest		  ) { orderId = ((ReleaseRequest		   ) caseAnalysis).getLink().getHref(); newStateTarget = "4";  }
	if ( caseAnalysis instanceof ReplaceOrderRequest	  ) { orderId = ((ReplaceOrderRequest	   ) caseAnalysis).getLink().getHref(); newStateTarget = "1";  }
	if ( caseAnalysis instanceof ReservationRequest		  ) { orderId = ((ReservationRequest	   ) caseAnalysis).getLink().getHref(); newStateTarget = "3";  }
	else                                                        {  orderId = null; newStateTarget = "12"; }
	
	Tuple<String, String> p = orderTracker.get(orderId);

	if(p == null)
        {
		p = new Tuple<String, String>("1", "1");
		orderTracker.put(orderId, p);
        }

	orderTracker.put(orderId, new Tuple<String, String>(p._2, newStateTarget));

	List<String> result = new LinkedList<String>();

	result.add(p._1);
	result.add(p._2);
	result.add(p._2);
	result.add(newStateTarget);

	return result;

    }
    


}
