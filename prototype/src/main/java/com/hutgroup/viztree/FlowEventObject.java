package com.hutgroup.viztree;

public class FlowEventObject {


    public static final String CANCEL_ORDER_REQUEST_STRING = "CANCEL_ORDER_REQUEST ";
    public static final String CHARGE_INVOICE_REQUEST_STRING = "CHARGE_INVOICE_REQUEST";
    public static final String DESPATCH_EVENT_STRING = "DESPATCH_EVENT";
    public static final String FRAUD_CHECK_REQUEST_STRING = "FRAUD_CHECK_REQUEST";
    public static final String FRAUD_STATUS_UPDATE_STRING = "FRAUD_STATUS_UPDATE";
    public static final String FULFILMENT_REQUEST_STRING = "FULFILMENT_REQUEST";
    public static final String INVOICE_FAILURE_EVENT_STRING = "INVOICE_FAILURE_EVENT";
    public static final String INVOICE_RETRY_EVENT_STRING = "INVOICE_RETRY_EVENT";
    public static final String INVOICE_SUCCESS_EVENT_STRING = "INVOICE_SUCCESS_EVENT";
    public static final String NEW_INVOICE_REQUEST_STRING = "NEW_INVOICE_REQUEST";
    public static final String NEW_ORDER_REQUEST_STRING = "NEW_ORDER_REQUEST";
    public static final String PAYRESOLVE_REFULFILMENT_REQUEST_STRING = "PAYRESOLVE_REFULFILMENT_REQUEST";
    public static final String REFUND_ORDER_REQUEST_STRING = "REFUND_ORDER_REQUEST";
    public static final String RELEASE_REQUEST_STRING = "RELEASE_REQUEST";
    public static final String REPLACE_ORDER_REQUEST_STRING = "REPLACE_ORDER_REQUEST";
    public static final String RESERVATION_REQUEST_STRING = "RESERVATION_REQUEST";
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

