package com.kiwiko.library.http.client.api.errors;

public class RequestTimeoutException extends ServerException {

    public RequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
