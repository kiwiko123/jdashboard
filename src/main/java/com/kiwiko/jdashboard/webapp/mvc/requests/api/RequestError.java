package com.kiwiko.jdashboard.webapp.mvc.requests.api;

public class RequestError extends RuntimeException {

    public RequestError(String message) {
        super(message);
    }

    public RequestError(String message, Throwable cause) {
        super(message, cause);
    }
}
