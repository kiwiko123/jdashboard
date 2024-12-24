package com.kiwiko.jdashboard.userauth.service.api.interfaces.exceptions;

public class IncorrectPasswordException extends UserAuthenticationException {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
