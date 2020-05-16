package com.kiwiko.webapp.mvc.interceptors.data;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class PostMethodContext extends MethodContext {

    private final @Nullable Object methodResult;
    private @Nullable Exception exception;

    public PostMethodContext(MethodInvocation methodInvocation, @Nullable Object methodResult, @Nullable Exception exception) {
        super(methodInvocation);
        this.methodResult = methodResult;
        this.exception = exception;
    }

    /**
     * @return the returned value of the method
     */
    @Nullable
    public Object getMethodResult() {
        return methodResult;
    }

    /**
     * @return the exception that the annotated method threw, if any
     */
    public Optional<Exception> getException() {
        return Optional.ofNullable(exception);
    }

    public void clearException() {
        exception = null;
    }
}
