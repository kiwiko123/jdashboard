package com.kiwiko.jdashboard.userauth.service.api.interfaces.exceptions;

public class CreateUserException extends Exception {

    public CreateUserException(String message) {
        super(message);
    }

    public CreateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
