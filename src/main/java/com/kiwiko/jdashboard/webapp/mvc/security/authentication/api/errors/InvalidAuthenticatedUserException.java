package com.kiwiko.jdashboard.webapp.mvc.security.authentication.api.errors;

public class InvalidAuthenticatedUserException extends RuntimeException {

    public InvalidAuthenticatedUserException(String message) {
        super(message);
    }
}
