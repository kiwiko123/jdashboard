package com.kiwiko.jdashboard.webapp.http.client.api.exceptions;

public class ServerException extends JdashboardApiClientException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}