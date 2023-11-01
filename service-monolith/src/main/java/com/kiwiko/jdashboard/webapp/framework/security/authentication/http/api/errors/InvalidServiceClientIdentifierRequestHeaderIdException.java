package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors;

public class InvalidServiceClientIdentifierRequestHeaderIdException extends UnauthorizedInternalRequestException {

    public InvalidServiceClientIdentifierRequestHeaderIdException(String message) {
        super(message);
    }
}
