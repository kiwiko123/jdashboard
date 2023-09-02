package com.kiwiko.jdashboard.userauth.service.api.interfaces.exceptions;

public class InvalidUsernameException extends UserAuthenticationException {

    public InvalidUsernameException(String message) {
        super(message);
    }
}
