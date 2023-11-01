package com.kiwiko.jdashboard.library.http.client.exceptions;

public class ServerException extends ApiClientException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
