package com.kiwiko.jdashboard.webapp.http.client.api.exceptions;

public class ClientException extends JdashboardApiClientException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
