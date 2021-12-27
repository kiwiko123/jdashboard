package com.kiwiko.jdashboard.webapp.http.client.api.exceptions;

public class JdashboardApiClientException extends Exception {

    public JdashboardApiClientException(String message) {
        super(message);
    }

    public JdashboardApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
