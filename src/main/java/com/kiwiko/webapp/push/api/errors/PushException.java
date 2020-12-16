package com.kiwiko.webapp.push.api.errors;

public class PushException extends RuntimeException {

    public PushException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushException(String message) {
        super(message);
    }
}
