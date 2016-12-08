package com.hutgroup.viztree;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Enumeration;

/**
 * Created by CoutinhoJ on 08/12/2016.
 */
public class MockTextMessage implements TextMessage {
    private String msg;

    public MockTextMessage(String msg) {
        this.msg = msg;
    }

    @Override
    public void setText(String s) throws JMSException {
       msg = s;
    }

    @Override
    public String getText() throws JMSException {
        return msg;
    }

    @Override
    public String getJMSMessageID() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSMessageID(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public long getJMSTimestamp() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSTimestamp(long l) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSCorrelationIDAsBytes(byte[] bytes) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSCorrelationID(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public String getJMSCorrelationID() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public Destination getJMSReplyTo() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSReplyTo(Destination destination) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public Destination getJMSDestination() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSDestination(Destination destination) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public int getJMSDeliveryMode() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSDeliveryMode(int i) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public boolean getJMSRedelivered() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSRedelivered(boolean b) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public String getJMSType() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSType(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public long getJMSExpiration() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSExpiration(long l) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public int getJMSPriority() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setJMSPriority(int i) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void clearProperties() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public boolean propertyExists(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public boolean getBooleanProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public byte getByteProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public short getShortProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public int getIntProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public long getLongProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public float getFloatProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public double getDoubleProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public String getStringProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public Object getObjectProperty(String s) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public Enumeration getPropertyNames() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setBooleanProperty(String s, boolean b) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setByteProperty(String s, byte b) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setShortProperty(String s, short i) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setIntProperty(String s, int i) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setLongProperty(String s, long l) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setFloatProperty(String s, float v) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setDoubleProperty(String s, double v) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setStringProperty(String s, String s1) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void setObjectProperty(String s, Object o) throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void acknowledge() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public void clearBody() throws JMSException {
        throw new RuntimeException("Unimplemented");
    }
}


