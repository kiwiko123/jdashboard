package com.kiwiko.mvc.interceptors.data;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class PostMethodContext extends MethodContext {

    private final Optional<Object> methodResult;
    private final Optional<Throwable> exception;

    public PostMethodContext(MethodInvocation methodInvocation, @Nullable Object methodResult, @Nullable Throwable exception) {
        super(methodInvocation);
        this.methodResult = Optional.ofNullable(methodResult);
        this.exception = Optional.ofNullable(exception);
    }

    public Optional<Object> getMethodResult() {
        return methodResult;
    }

    public Optional<Throwable> getException() {
        return exception;
    }
}
