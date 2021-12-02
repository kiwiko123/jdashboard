package com.kiwiko.jdashboard.webapp.mvc.requests.internal.data;

public class StackMethodData {

    private Class<?> clazz;
    private String methodName;

    public Class<?> getClazz() {
        return clazz;
    }

    public StackMethodData setClazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public StackMethodData setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }
}
