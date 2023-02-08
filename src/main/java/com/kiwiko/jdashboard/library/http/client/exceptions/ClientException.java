package com.kiwiko.jdashboard.library.http.client.exceptions;

public class ClientException extends ApiClientException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
