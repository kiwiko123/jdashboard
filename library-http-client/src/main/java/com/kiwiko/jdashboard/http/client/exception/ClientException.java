package com.kiwiko.jdashboard.http.client.exception;

public class ClientException extends HttpClientException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
