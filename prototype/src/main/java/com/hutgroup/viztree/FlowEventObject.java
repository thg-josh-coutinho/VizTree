package com.hutgroup.viztree;

public class FlowEventObject {

    private String userName;
    private String edge;
    private double weight;

    public FlowEventObject() {
    }

    public FlowEventObject(String userName, String edge, double weight) {
        super();
        this.userName = userName;
	this.edge = edge;
	this.weight = weight;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEdge() {
        return edge;
    }
    public void setEdge(String edge) {
        this.edge = edge;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

}
