package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors;

public class UnauthorizedInternalRequestException extends Exception {

    public UnauthorizedInternalRequestException(String message) {
        super(message);
    }

    public UnauthorizedInternalRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
