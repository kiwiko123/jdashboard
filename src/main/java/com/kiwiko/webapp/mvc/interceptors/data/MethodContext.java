package com.kiwiko.webapp.mvc.interceptors.data;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MethodContext {

    private final List<Object> methodArguments;
    private final Optional<Object> instance;
    private final Method method;

    public MethodContext(MethodInvocation invocation) {
        methodArguments = Arrays.stream(invocation.getArguments())
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        instance = Optional.ofNullable(invocation.getThis());
        method = invocation.getMethod();
    }

    public List<Object> getMethodArguments() {
        return methodArguments;
    }

    /**
     * @return the instance of the annotated method's class
     */
    public Optional<Object> getInstance() {
        return instance;
    }

    /**
     * @return the annotated method that's being intercepted
     */
    public Method getMethod() {
        return method;
    }
}
