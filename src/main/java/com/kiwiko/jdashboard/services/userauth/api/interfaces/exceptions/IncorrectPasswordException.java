package com.kiwiko.jdashboard.services.userauth.api.interfaces.exceptions;

public class IncorrectPasswordException extends UserAuthenticationException {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
