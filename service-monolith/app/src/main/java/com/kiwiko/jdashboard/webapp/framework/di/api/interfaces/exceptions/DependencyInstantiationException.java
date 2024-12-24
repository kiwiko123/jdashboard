package com.kiwiko.jdashboard.webapp.framework.di.api.interfaces.exceptions;

public class DependencyInstantiationException extends RuntimeException {

    public DependencyInstantiationException(String message) {
        super(message);
    }

    public DependencyInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
