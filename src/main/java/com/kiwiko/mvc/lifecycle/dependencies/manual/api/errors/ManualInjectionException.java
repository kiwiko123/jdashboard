package com.kiwiko.mvc.lifecycle.dependencies.manual.api.errors;

public class ManualInjectionException extends RuntimeException {

    public ManualInjectionException(String message) {
        super(message);
    }

    public ManualInjectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
