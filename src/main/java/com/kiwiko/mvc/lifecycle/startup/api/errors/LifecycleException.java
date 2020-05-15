package com.kiwiko.mvc.lifecycle.startup.api.errors;

public class LifecycleException extends RuntimeException {

    public LifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(Throwable cause) {
        super(cause);
    }
}
