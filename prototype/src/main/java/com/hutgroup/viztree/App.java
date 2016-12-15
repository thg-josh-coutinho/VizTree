package com.hutgroup.viztree;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.*;
import java.util.*;

import com.hutgroup.viztree.graph.FlowGraphListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.jgrapht.event.FlowGraphEdgeChangeEvent;
import org.jgrapht.graph.FlowGraph;
import org.jgrapht.graph.FlowGraphNode;


public class App {

    public static final String PROPERTIES_FILE = "app.properties";

    public static final int EVENT_DELAY = 200;
    private FlowGraph graph;
    private Stream<List<FlowGraphEdgeChangeEvent>> edgeEventStream;
    boolean messageLimit;
    int numMessages;

    public App(Stream<List<FlowGraphEdgeChangeEvent>> inputStream, FlowGraph graph) {
        this.edgeEventStream = inputStream;
        this.graph = graph;
    }

    public App(Stream<List<FlowGraphEdgeChangeEvent>> inputStream, FlowGraph graph, int numMessages) {
        this.edgeEventStream = inputStream;
        this.graph = graph;
        this.numMessages = numMessages;
        messageLimit = true;
    }


    public static void main(String[] args) throws Exception {

        Properties properties = initProperties();
        FlowGraph graph = initGraph(properties);

        new App(initFlowGraphStream(graph, properties), graph).runApp();
    }

    /**
     * Updates the graph with each 'graphUpdate' pulled from the
     * MessageConsumer stream.
     */
    public void runApp() throws Exception // Remove this throws exception
    {
        if (messageLimit) {
            edgeEventStream.limit(numMessages).forEach((graphUpdate) -> {
                for (FlowGraphEdgeChangeEvent upd : graphUpdate) {
                    update(graph, upd);
                }
            });
        } else {
            edgeEventStream.forEach((graphUpdate) -> {
                for (FlowGraphEdgeChangeEvent upd : graphUpdate) {
                    update(graph, upd);
                }
            });
        }
    }


    /**
     * Updates the edge with the new weight recieved from the event.
     * Then waits to space out updates.
     */
    private void update(FlowGraph g, FlowGraphEdgeChangeEvent update) {
        if (update == null) {
            System.err.println("Null update");
            return;
        }

        g.setEdgeWeight(update.getEdge(), update.getNewWeight());


        // remove this eventually
        try {
            Thread.sleep(EVENT_DELAY);
        } catch (Exception e) {
            System.err.println("Err occurred when pausing within update inside App.java");
            e.printStackTrace();
        }

    }


    private static FlowGraphNode getNode(Map<String, FlowGraphNode> nodeMap, String vertex, FlowGraph g) {
        if (nodeMap.get(vertex) == null) {
            nodeMap.put(vertex, new FlowGraphNode(vertex));
        }
        return nodeMap.get(vertex);
    }


    public static FlowGraph initGraph(Properties properties) throws FileNotFoundException {
        FlowGraph graph = new FlowGraph();
        graph.addGraphListener(new FlowGraphListener());

        Map<String, FlowGraphNode> nodeMap = new HashMap<>();
        Scanner sc = new Scanner(new FileInputStream(retrieveFile(properties.getProperty("GRAPH_CONFIG_FILE"))));
        while (sc.hasNext()) {
            String sourceVertex = sc.next();
            String targetVertex = sc.next();
            FlowGraphNode sourceNode = getNode(nodeMap, sourceVertex, graph);
            FlowGraphNode targetNode = getNode(nodeMap, targetVertex, graph);
            graph.addEdge(sourceNode, targetNode);
            FlowGraphNode va = new FlowGraphNode("a");
            graph.addVertex(va);
            graph.setEdgeWeight(graph.addEdge(sourceNode, targetNode), 1);

        }
        return graph;
    }


    private static Stream<List<FlowGraphEdgeChangeEvent>> initFlowGraphStream(FlowGraph graph, Properties properties) throws JMSException {

        Session session = initSession(properties);

        return new FlowGraphEventStream(
                session.createConsumer(session.createQueue(properties.getProperty("QUEUE_NAME"))),
                graph,
                initConfigMap(properties)).stream();

    }

    private static Session initSession(Properties properties) {
        try {

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getProperty("BROKER_ADDRESS"));
            Connection connection = connectionFactory.createQueueConnection("admin", "admin");
            connection.start();

            return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static Properties initProperties() throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(retrieveFile(PROPERTIES_FILE)));
        return p;
    }

    private static File retrieveFile(String fileName) throws FileNotFoundException {

        return new File(
                App.class.getClassLoader().getResource(fileName).getFile());
    }

    private static Map<String, String> initConfigMap(Properties properties) {

        Map<String, String> result = new HashMap<>();
        Scanner configFile = null;
        try {
            configFile = new Scanner(new FileInputStream(retrieveFile((String) properties.get("CONFIG_FILE"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (configFile.hasNext()) {
            result.put(configFile.next(), configFile.next());
        }

        return result;

    }
}
