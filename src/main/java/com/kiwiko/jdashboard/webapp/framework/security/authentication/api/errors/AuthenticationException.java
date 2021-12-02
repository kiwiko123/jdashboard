package com.kiwiko.jdashboard.webapp.framework.security.authentication.api.errors;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}
