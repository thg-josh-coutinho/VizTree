package com.hutgroup.viztree;

import org.jgrapht.event.FlowGraphEdgeChangeEvent;

import javax.jms.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by CoutinhoJ on 08/12/2016.
 */
class MockMessageConsumer implements MessageConsumer {
    int counter;
    List<String> messages;

    public MockMessageConsumer(List<String> l) {
        this.messages = l;
    }


    public String getMessageSelector() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    public MessageListener getMessageListener() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    public void setMessageListener(MessageListener var1) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    public Message receive() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    public Message receive(long var1) throws JMSException {
        return new MockTextMessage(nextUpdate());
    }

    public Message receiveNoWait() throws JMSException {
       return new MockTextMessage(nextUpdate());
    }

    public void close() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    private String nextUpdate() {
        return messages.get((counter++) % messages.size());

    }

    public Stream<String> stream() {

        return Stream.iterate(nextUpdate(),
                prev -> nextUpdate());
    }


}
