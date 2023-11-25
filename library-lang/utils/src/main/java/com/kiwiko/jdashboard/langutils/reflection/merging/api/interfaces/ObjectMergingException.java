package com.kiwiko.jdashboard.langutils.reflection.merging.api.interfaces;

public class ObjectMergingException extends RuntimeException {

    public ObjectMergingException(String message) {
        super(message);
    }

    public ObjectMergingException(String message, Throwable cause) {
        super(message, cause);
    }
}
