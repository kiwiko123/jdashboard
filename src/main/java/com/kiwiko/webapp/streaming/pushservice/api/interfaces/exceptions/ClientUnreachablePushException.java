package com.kiwiko.webapp.streaming.pushservice.api.interfaces.exceptions;

public class ClientUnreachablePushException extends PushException {

    public ClientUnreachablePushException(String message) {
        super(message);
    }
}
