package com.kiwiko.library.lang.reflection.properties.api.errors;

public class PropertyMappingException extends RuntimeException {

    public PropertyMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyMappingException(String message) {
        super(message);
    }
}
