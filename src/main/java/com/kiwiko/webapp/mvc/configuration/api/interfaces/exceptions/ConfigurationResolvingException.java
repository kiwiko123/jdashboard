package com.kiwiko.webapp.mvc.configuration.api.interfaces.exceptions;

public class ConfigurationResolvingException extends RuntimeException {

    public ConfigurationResolvingException(String message) {
        super(message);
    }

    public ConfigurationResolvingException(String message, Throwable cause) {
        super(message, cause);
    }
}
