package com.kiwiko.jdashboard.library.http.client.api.exceptions;

public class ApiClientRuntimeException extends RuntimeException {

    public ApiClientRuntimeException(String message) {
        super(message);
    }

    public ApiClientRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiClientRuntimeException(Throwable cause) {
        super(cause);
    }
}
