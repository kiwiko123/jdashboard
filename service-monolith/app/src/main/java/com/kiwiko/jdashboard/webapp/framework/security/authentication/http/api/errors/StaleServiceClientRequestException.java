package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors;

public class StaleServiceClientRequestException extends UnauthorizedInternalRequestException {

    public StaleServiceClientRequestException(String message) {
        super(message);
    }
}
