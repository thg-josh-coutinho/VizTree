package com.hutgroup.viztree;

public class TestControlMessageObject {


    private String functionName;
    private Object testResult;

    public TestControlMessageObject() {
    }

    public FlowEventObject(String f, String t) {
        super();
	functionName = f;
	testResult = t;
    }

    public String getFunctionName() {
        return functionName
    }

    public void setFunctionName(String f) {
        this.functionName = f;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

}

