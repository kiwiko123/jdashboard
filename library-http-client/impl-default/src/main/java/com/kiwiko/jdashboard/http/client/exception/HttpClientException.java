package com.kiwiko.jdashboard.http.client.exception;

public class HttpClientException extends Exception {
    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
