package com.kiwiko.jdashboard.http.client.exception;

public class RequestTimeoutException extends ServerException {

    public RequestTimeoutException(String message) {
        super(message);
    }

    public RequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
