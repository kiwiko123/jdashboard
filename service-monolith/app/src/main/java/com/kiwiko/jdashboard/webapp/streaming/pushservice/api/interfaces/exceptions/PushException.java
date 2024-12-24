package com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.exceptions;

public class PushException extends Exception {

    public PushException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushException(String message) {
        super(message);
    }
}
