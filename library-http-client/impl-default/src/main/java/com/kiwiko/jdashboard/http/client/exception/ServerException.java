package com.kiwiko.jdashboard.http.client.exception;

public class ServerException extends HttpClientException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
