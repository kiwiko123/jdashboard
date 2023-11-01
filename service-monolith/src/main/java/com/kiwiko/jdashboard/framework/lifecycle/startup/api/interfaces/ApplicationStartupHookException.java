package com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces;

public class ApplicationStartupHookException extends Exception {

    public ApplicationStartupHookException(String message) {
        super(message);
    }

    public ApplicationStartupHookException(String message, Throwable cause) {
        super(message, cause);
    }
}
