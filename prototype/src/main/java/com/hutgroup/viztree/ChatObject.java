package com.hutgroup.viztree;

public class ChatObject {

    private String userName;
    private String edge;
    private double weight;

    public ChatObject() {
    }

    public ChatObject(String userName, String edge, double weight) {
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

}
