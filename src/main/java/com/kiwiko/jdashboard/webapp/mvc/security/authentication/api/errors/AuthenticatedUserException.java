package com.kiwiko.jdashboard.webapp.mvc.security.authentication.api.errors;

public class AuthenticatedUserException extends RuntimeException {

    public AuthenticatedUserException(String message) {
        super(message);
    }
}