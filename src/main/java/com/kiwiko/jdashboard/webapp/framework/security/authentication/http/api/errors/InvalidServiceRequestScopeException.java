package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors;

public class InvalidServiceRequestScopeException extends UnauthorizedInternalRequestException {

    public InvalidServiceRequestScopeException(String message) {
        super(message);
    }

    public InvalidServiceRequestScopeException(String message, Throwable cause) {
        super(message, cause);
    }
}
