package com.kiwiko.jdashboard.library.files.properties.readers.api.interfaces.exceptions;

public class PropertyFileException extends RuntimeException {

    public PropertyFileException(String message) {
        super(message);
    }

    public PropertyFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
