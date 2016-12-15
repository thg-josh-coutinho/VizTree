package com.hutgroup.viztree;

public class TestControlMessageObject {


    private String testFunction;
    private Object testResult;

    public TestControlMessageObject() {
    }

    public TestControlMessageObject(String f, Object t) {
        super();
        testFunction = f;
        testResult = t;
    }

    public String getTestFunction() {
        return testFunction;
    }

    public void setTestFunction(String f) {
        this.testFunction = f;
    }

    public Object getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

}

