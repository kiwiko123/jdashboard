package com.kiwiko.jdashboard.webapp.mvc.json.deserialization.api.interfaces.exceptions;

public class RequestBodyDeserializationException extends RuntimeException {

    public RequestBodyDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
