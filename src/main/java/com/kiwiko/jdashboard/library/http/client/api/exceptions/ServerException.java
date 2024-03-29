package com.kiwiko.jdashboard.library.http.client.api.exceptions;

public class ServerException extends ApiClientException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
