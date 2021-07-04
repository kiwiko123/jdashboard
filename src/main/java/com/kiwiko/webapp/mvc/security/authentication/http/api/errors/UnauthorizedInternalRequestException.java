package com.kiwiko.webapp.mvc.security.authentication.http.api.errors;

public class UnauthorizedInternalRequestException extends Exception {

    public UnauthorizedInternalRequestException(String message) {
        super(message);
    }
}
