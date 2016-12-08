package com.hutgroup.viztree;

import com.hutgroup.viztree.graph.FlowGraphListener;
import com.sun.org.apache.bcel.internal.generic.NEW;
import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.tcp.ExceededMaximumConnectionsException;
import org.jgrapht.event.FlowGraphEdgeChangeEvent;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.*;

import com.josh.utils.*;
import org.jgrapht.graph.FlowGraph;
import org.jgrapht.graph.FlowGraphEdge;
import org.jgrapht.graph.FlowGraphNode;

/**
 * Created by CoutinhoJ on 07/12/2016.
 */
public class FlowGraphEventStreamTest extends TestCase {

    private static final String NEW_ORDER_REQUEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<newOrderRequest eventId=\"2228257\" eventTime=\"2016-12-07T15:57:34Z\" startTime=\"2016-12-07T15:57:35Z\" finishTime=\"2016-12-07T15:57:35Z\" eventType=\"NEW_ORDER_REQUEST\" xmlns=\"http://xml.thehutgroup.com/return-shipment\">\n" +
            "    <link rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303230\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "    <orderNumber>CatTest303230</orderNumber>\n" +
            "    <orderLink rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303230\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "</newOrderRequest>";

    private static final String NEW_ORDER_REQUEST_XML_2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<newOrderRequest eventId=\"2228250\" eventTime=\"2016-12-07T15:57:34Z\" startTime=\"2016-12-07T15:57:34Z\" finishTime=\"2016-12-07T15:57:34Z\" eventType=\"NEW_ORDER_REQUEST\" xmlns=\"http://xml.thehutgroup.com/return-shipment\">\n" +
            "    <link rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303227\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "    <orderNumber>CatTest303227</orderNumber>\n" +
            "    <orderLink rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303227\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "</newOrderRequest>";

    private static final String RESERVATION_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<reservationResponse eventId=\"2228265\" eventTime=\"2016-12-07T15:57:47Z\" startTime=\"2016-12-07T15:57:47Z\" finishTime=\"2016-12-07T15:57:48Z\" eventType=\"RESERVATION_RESPONSE\" xmlns=\"http://xml.thehutgroup.com/return-shipment\">\n" +
            "    <link rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303229\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "    <orderNumber>CatTest303229</orderNumber>\n" +
            "    <fulfilmentRequestLine productId=\"10606205\" quantity=\"1\"/>\n" +
            "    <reservationReference>273509-1</reservationReference>\n" +
            "    <responseMessage>ALLOCATION_SUCCESS</responseMessage>\n" +
            "    <shipmentLink rel=\"shipment\" href=\"http://sherlock.st.io.thehut.local:8080/sherlock/shipment/15860119\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "</reservationResponse>";

    private static final String RESERVATION_REQUEST = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<reservationRequest eventId=\"2228254\" eventTime=\"2016-12-07T15:57:34Z\" startTime=\"2016-12-07T15:57:35Z\" finishTime=\"2016-12-07T15:57:35Z\" eventType=\"RESERVATION_REQUEST\" xmlns=\"http://xml.thehutgroup.com/return-shipment\">\n" +
            "    <link rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303227\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "    <orderNumber>CatTest303227</orderNumber>\n" +
            "    <fulfilmentRequestLine productId=\"10597776\" quantity=\"1\"/>\n" +
            "    <reservationReference>273507-1</reservationReference>\n" +
            "</reservationRequest>";

    private static final String RELEASE_REQUEST = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<releaseRequest eventId=\"2228264\" eventTime=\"2016-12-07T15:57:46Z\" startTime=\"2016-12-07T15:57:46Z\" finishTime=\"2016-12-07T15:57:47Z\" eventType=\"RELEASE_REQUEST\" xmlns=\"http://xml.thehutgroup.com/return-shipment\">\n" +
            "    <link rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303230\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "    <shipmentLink rel=\"shipment\" href=\"http://sherlock.st.io.thehut.local:8080/sherlock/shipment/15860118\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "</releaseRequest>";
    private static final String NEW_INVOICE_REQUEST = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<newInvoiceRequest eventId=\"2228263\" eventTime=\"2016-12-07T15:57:45Z\" startTime=\"2016-12-07T15:57:46Z\" finishTime=\"2016-12-07T15:57:46Z\" eventType=\"NEW_INVOICE_REQUEST\" xmlns=\"http://xml.thehutgroup.com/return-shipment\">\n" +
            "    <link rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303230\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "    <orderNumber>CatTest303230</orderNumber>\n" +
            "    <fulfilmentRequestLine productId=\"10606205\" quantity=\"1\"/>\n" +
            "    <invoiceLink rel=\"invoice\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/invoice/304116\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "</newInvoiceRequest>";
    private static final String RELEASE_REQUEST_2 = "<releaseRequest eventId=\"2228261\" eventTime=\"2016-12-07T15:57:44Z\" startTime=\"2016-12-07T15:57:45Z\" finishTime=\"2016-12-07T15:57:46Z\" eventType=\"RELEASE_REQUEST\" xmlns=\"http://xml.thehutgroup.com/return-shipment\">\n" +
            "    <link rel=\"order\" href=\"http://ordermanager.st.io.thehut.local:8080/OrderManager/order/CatTest303227\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "    <shipmentLink rel=\"shipment\" href=\"http://sherlock.st.io.thehut.local:8080/sherlock/shipment/15860117\" mediaType=\"application/vnd.thehutgroup.com+xml\"/>\n" +
            "</releaseRequest>";
    // private static final String RESERVATION_REQUEST = "";
    //private static final String RESERVATION_REQUEST = "";


    private static final String ORDER_EVENT_PACKAGE_NAME = "com.hutgroup.viztree.orderevents";
    private static final String QUEUE_NAME = "SampleQueue";
    FlowGraphEventStream eventStream;
    Map<String, String> configMap;

    public FlowGraphEventStreamTest(String testName) {
        super(testName);
    }

    @Override
    public void setUp() throws FileNotFoundException{
        configMap = readConfigMap();
    }

    public static Test suite() {
        return new TestSuite(FlowGraphEventStreamTest.class);
    }

    public void testSendMessage() throws Exception {

        //send(NEW_ORDER_REQUEST_XML);
        //eventStream.stream().allMatch((es) -> isNewOrderRequestTransition(es));

    }

    private Map<String, String> readConfigMap() throws FileNotFoundException {

        Map<String, String> result = new HashMap<>();

        ClassLoader classLoader = getClass().getClassLoader();
        Scanner configFile = new Scanner(
                    new FileInputStream(
                            new File(
                                    classLoader.getResource("ForwardingMap.txt").getFile())
                    )
            );

        while(configFile.hasNext()){
            result.put(configFile.next(), configFile.next());
        }

        return result;

    }

    public void test_deserialize_new_order_request_message() throws FileNotFoundException {
        Tuple<MockMessageConsumer, FlowGraph> pair = initGraph();

        eventStream = new FlowGraphEventStream(pair._1, pair._2, configMap);

        List<String> output = eventStream.unmarshallOrderManagerEdgeEvent(NEW_ORDER_REQUEST_XML);
        assertEquals("2", output.get(3));

        List<String> output2 = eventStream.unmarshallOrderManagerEdgeEvent(NEW_ORDER_REQUEST_XML_2);
        assertEquals("2", output2.get(3));
    }

    public void test_deserialize_reservation_request() throws FileNotFoundException {
        Tuple<MockMessageConsumer, FlowGraph> pair = initGraph();

        eventStream = new FlowGraphEventStream(pair._1, pair._2, configMap);

        List<String> output = eventStream.unmarshallOrderManagerEdgeEvent(RESERVATION_REQUEST);
        assertEquals("3", output.get(3));
    }

    public void test_deserialize_new_invoice_request() throws FileNotFoundException {
        Tuple<MockMessageConsumer, FlowGraph> pair = initGraph();

        eventStream = new FlowGraphEventStream(pair._1, pair._2, configMap);

        List<String> output = eventStream.unmarshallOrderManagerEdgeEvent(NEW_INVOICE_REQUEST);
        assertEquals("2", output.get(3));
    }

    public void test_deserialize_release_request() throws FileNotFoundException {
        Tuple<MockMessageConsumer, FlowGraph> pair = initGraph();

        eventStream = new FlowGraphEventStream(pair._1, pair._2, configMap);

        List<String> output = eventStream.unmarshallOrderManagerEdgeEvent(RELEASE_REQUEST);
        assertEquals("4", output.get(3));

        List<String> output2 = eventStream.unmarshallOrderManagerEdgeEvent(RELEASE_REQUEST_2);
        assertEquals("4", output2.get(3));
    }


    private void send(String a) {
        throw new RuntimeException("Unimplemented");
    }

    private boolean isNewOrderRequestTransition(List<FlowGraphEdgeChangeEvent> es) {
        throw new RuntimeException("Unimplemented");
    }

    private static Tuple<MockMessageConsumer, FlowGraph> initGraph() {

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

        //----------------End of definition of the graph ----------------------------------


        List<String> msgs = new LinkedList<>();
        msgs.add(NEW_ORDER_REQUEST_XML);
        msgs.add(NEW_ORDER_REQUEST_XML_2);
        msgs.add(RESERVATION_RESPONSE);
        msgs.add(RESERVATION_REQUEST);
        msgs.add(RELEASE_REQUEST);
        msgs.add(NEW_INVOICE_REQUEST );
        msgs.add(RELEASE_REQUEST_2);

        Tuple<MockMessageConsumer, FlowGraph> pair
                = new Tuple<>(new MockMessageConsumer(msgs), graph);

        return pair;
    }

    private static MessageConsumer initActiveMQConsumer() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(QUEUE_NAME);

            return session.createConsumer(destination);

        } catch (Exception e) {
            System.out.println("Could not connect to activemq");
            System.exit(1);
        }
        return null;
    }

}