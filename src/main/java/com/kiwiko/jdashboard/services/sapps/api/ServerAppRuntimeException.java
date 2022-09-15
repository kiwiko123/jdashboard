package com.kiwiko.jdashboard.services.sapps.api;

public class ServerAppRuntimeException extends RuntimeException {

    public ServerAppRuntimeException() {
    }

    public ServerAppRuntimeException(String message) {
        super(message);
    }

    public ServerAppRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
