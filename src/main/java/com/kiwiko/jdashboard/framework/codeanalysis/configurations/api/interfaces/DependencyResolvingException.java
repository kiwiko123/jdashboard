package com.kiwiko.jdashboard.framework.codeanalysis.configurations.api.interfaces;

public class DependencyResolvingException extends RuntimeException {

    public DependencyResolvingException(String message) {
        super(message);
    }

    public DependencyResolvingException(String message, Throwable cause) {
        super(message, cause);
    }
}
