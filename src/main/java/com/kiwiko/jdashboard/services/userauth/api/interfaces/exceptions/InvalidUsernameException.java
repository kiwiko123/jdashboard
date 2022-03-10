package com.kiwiko.jdashboard.services.userauth.api.interfaces.exceptions;

public class InvalidUsernameException extends UserAuthenticationException {

    public InvalidUsernameException(String message) {
        super(message);
    }
}
