package com.kiwiko.jdashboard.library.http.client.api.exceptions;

public class ApiClientPluginException extends ApiClientRuntimeException {

    public ApiClientPluginException(String message) {
        super(message);
    }

    public ApiClientPluginException(String message, Throwable cause) {
        super(message, cause);
    }
}
