package com.kiwiko.library.http.client.api.exceptions;

public class JdashboardApiClientRuntimeException extends RuntimeException {

    public JdashboardApiClientRuntimeException(String message) {
        super(message);
    }

    public JdashboardApiClientRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdashboardApiClientRuntimeException(Throwable cause) {
        super(cause);
    }
}
