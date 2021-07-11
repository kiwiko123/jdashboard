package com.kiwiko.webapp.persistence.data.cdc.api.interfaces.exceptions;

public class CaptureEntityDataChangeException extends RuntimeException {

    public CaptureEntityDataChangeException(String message) {
        super(message);
    }

    public CaptureEntityDataChangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
