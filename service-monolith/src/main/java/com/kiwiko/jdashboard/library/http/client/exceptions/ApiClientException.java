package com.kiwiko.jdashboard.library.http.client.exceptions;

public class ApiClientException extends Exception {

    public ApiClientException(String message) {
        super(message);
    }

    public ApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
