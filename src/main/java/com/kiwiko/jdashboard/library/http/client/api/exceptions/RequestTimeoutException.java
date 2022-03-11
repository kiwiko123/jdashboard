package com.kiwiko.jdashboard.library.http.client.api.exceptions;

public class RequestTimeoutException extends ServerException {

    public RequestTimeoutException(String message) {
        super(message);
    }

    public RequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
