package com.hutgroup.viztree;

import com.hutgroup.viztree.graph.FlowGraphListener;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jgrapht.event.FlowGraphEdgeChangeEvent;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.*;
import java.util.stream.*;
import com.josh.utils.*;
import org.jgrapht.graph.FlowGraph;
import org.jgrapht.graph.FlowGraphEdge;
import org.jgrapht.graph.FlowGraphNode;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    public void testApp() throws  Exception {

        Tuple<Stream<List<FlowGraphEdgeChangeEvent>>, FlowGraph> t = initGraph();

        App app = new App(t._1, t._2);

        app.runApp();
    }


    private static class MockMessageConsumer {
        int counter;
        List<FlowGraphEdgeChangeEvent> messages;

        public MockMessageConsumer(List<FlowGraphEdgeChangeEvent> l) {
            this.messages = l;
        }

        private List<FlowGraphEdgeChangeEvent> nextUpdate() {
            List<FlowGraphEdgeChangeEvent> event = new LinkedList<>();
            event.add(messages.get((counter++) % messages.size()));
            for(FlowGraphEdgeChangeEvent e : event){
                e.setNewWeight(Math.random()*30);
            }
            return event;
        }

        public Stream<List<FlowGraphEdgeChangeEvent>> stream() {

            return Stream.iterate(nextUpdate(),
                    prev -> nextUpdate());
        }


    }


    private static Tuple<Stream<List<FlowGraphEdgeChangeEvent>>, FlowGraph> initGraph() {

        FlowGraph graph = new FlowGraph();
        graph.addGraphListener(new FlowGraphListener());
        FlowGraphNode va = new FlowGraphNode("a");
        FlowGraphNode v1 = new FlowGraphNode("1");
        FlowGraphNode v2 = new FlowGraphNode("2");
        FlowGraphNode v3 = new FlowGraphNode("3");
        FlowGraphNode v4 = new FlowGraphNode("4");
        FlowGraphNode v5 = new FlowGraphNode("5");
        FlowGraphNode v6 = new FlowGraphNode("6");
        FlowGraphNode v7 = new FlowGraphNode("7");
        FlowGraphNode v8 = new FlowGraphNode("8");
        FlowGraphNode v9 = new FlowGraphNode("9");
        FlowGraphNode v10 = new FlowGraphNode("10");
        FlowGraphNode v11 = new FlowGraphNode("11");
        FlowGraphNode v12 = new FlowGraphNode("12");
        FlowGraphNode v13 = new FlowGraphNode("13");
        FlowGraphNode vl = new FlowGraphNode("l");
        FlowGraphNode v14 = new FlowGraphNode("14");
        FlowGraphNode veq = new FlowGraphNode("eq");
        FlowGraphNode vr = new FlowGraphNode("r");
        FlowGraphNode v21 = new FlowGraphNode("21");
        FlowGraphNode vex = new FlowGraphNode("ex");
        FlowGraphNode v22 = new FlowGraphNode("22");

        graph.addVertex(va);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);
        graph.addVertex(v8);
        graph.addVertex(v9);
        graph.addVertex(v10);
        graph.addVertex(v11);
        graph.addVertex(v12);
        graph.addVertex(v13);
        graph.addVertex(vl);
        graph.addVertex(v14);
        graph.addVertex(veq);
        graph.addVertex(vr);
        graph.addVertex(v21);
        graph.addVertex(vex);
        graph.addVertex(v22);


        FlowGraphEdge e1 = graph.addEdge(veq, v21);
        graph.setEdgeWeight(e1, 1);
        FlowGraphEdge e2 = graph.addEdge(v21, v1);
        graph.setEdgeWeight(e2, 1);
        FlowGraphEdge e3 = graph.addEdge(v1, v2);
        graph.setEdgeWeight(e3, 10);
        FlowGraphEdge e4 = graph.addEdge(v2, v3);
        graph.setEdgeWeight(e4, 1);
        FlowGraphEdge e5 = graph.addEdge(v3, v3);
        graph.setEdgeWeight(e5, 1);
        FlowGraphEdge e6 = graph.addEdge(v3, v14);
        graph.setEdgeWeight(e6, 1);
        FlowGraphEdge e6a = graph.addEdge(v3, v4);
        graph.setEdgeWeight(e6a, 1);
        FlowGraphEdge e7 = graph.addEdge(v14, v4);
        graph.setEdgeWeight(e7, 1);
        FlowGraphEdge e8 = graph.addEdge(v4, v22);
        graph.setEdgeWeight(e8, 1);
        FlowGraphEdge e8a = graph.addEdge(v4, v5);
        graph.setEdgeWeight(e8a, 1);
        FlowGraphEdge e9 = graph.addEdge(v22, v5);
        graph.setEdgeWeight(e9, 1);
        FlowGraphEdge e10 = graph.addEdge(v5, v6);
        graph.setEdgeWeight(e10, 1);
        FlowGraphEdge e11 = graph.addEdge(v6, v7);
        graph.setEdgeWeight(e11, 1);

        FlowGraphEdge e12 = graph.addEdge(v6, v8);
        graph.setEdgeWeight(e12, 1);

        FlowGraphEdge e13 = graph.addEdge(v1, v10);
        graph.setEdgeWeight(e13, 1);

        FlowGraphEdge e14 = graph.addEdge(v10, v11);
        graph.setEdgeWeight(e14, 1);

        FlowGraphEdge e15 = graph.addEdge(v11, v12);
        graph.setEdgeWeight(e15, 1);

        FlowGraphEdge e16 = graph.addEdge(v11, v13);
        graph.setEdgeWeight(e16, 1);

        FlowGraphEdge e17 = graph.addEdge(v4, v9);
        graph.setEdgeWeight(e17, 1);

        FlowGraphEdge e18 = graph.addEdge(v9, v2);
        graph.setEdgeWeight(e18, 1);

        FlowGraphEdge e19 = graph.addEdge(v2, v11);
        graph.setEdgeWeight(e19, 1);

        FlowGraphEdge e20 = graph.addEdge(v10, v1);
        graph.setEdgeWeight(e19, 1);

        FlowGraphEdge e21 = graph.addEdge(v1, v11);
        graph.setEdgeWeight(e19, 1);


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

        //----------------End of definition of the graph ----------------------------------


        List<FlowGraphEdgeChangeEvent> updateQueue = new LinkedList<>();

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


        //----------------End of definition of the queue ----------------------------------

        Tuple<Stream<List<FlowGraphEdgeChangeEvent>>, FlowGraph> pair
                = new Tuple<>(new MockMessageConsumer(updateQueue).stream(), graph);

        return pair;
    }


}
