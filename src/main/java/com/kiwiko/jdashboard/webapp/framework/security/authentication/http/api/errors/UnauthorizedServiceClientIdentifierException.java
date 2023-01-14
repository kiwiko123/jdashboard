package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors;

public class UnauthorizedServiceClientIdentifierException extends UnauthorizedInternalRequestException {

    public UnauthorizedServiceClientIdentifierException(String message) {
        super(message);
    }

    public UnauthorizedServiceClientIdentifierException(String message, Throwable cause) {
        super(message, cause);
    }
}
