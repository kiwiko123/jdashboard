package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors;

public class MissingServiceClientIdentifierRequestHeaderException extends UnauthorizedInternalRequestException {

    public MissingServiceClientIdentifierRequestHeaderException(String message) {
        super(message);
    }
}
