package com.kiwiko.memory.performance.api.errors;

public class ThrottleException extends RuntimeException {

    public ThrottleException(String message) {
        super(message);
    }
}
