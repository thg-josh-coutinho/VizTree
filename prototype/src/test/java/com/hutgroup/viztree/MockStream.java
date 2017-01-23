package com.hutgroup.viztree;

import org.jgrapht.event.FlowGraphEdgeChangeEvent;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by CoutinhoJ on 08/12/2016.
 */
public class MockStream {

    int counter;
    List<FlowGraphEdgeChangeEvent> messages;

    public MockStream(List<FlowGraphEdgeChangeEvent> l) {
        this.messages = l;
    }

    private List<FlowGraphEdgeChangeEvent> nextUpdate() {
        List<FlowGraphEdgeChangeEvent> es = new LinkedList<>();
        FlowGraphEdgeChangeEvent e = messages.get((counter++) % messages.size());
        e.setNewWeight(Math.random()*30);
        es.add(e);
        return es;

    }

    public Stream<List<FlowGraphEdgeChangeEvent>> stream() {

        return Stream.iterate(nextUpdate(),
                prev -> nextUpdate());
    }
}
