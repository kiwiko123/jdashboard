package com.kiwiko.mvc.security.authentication.api.errors;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }
}
