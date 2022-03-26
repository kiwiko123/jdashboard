package com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.exceptions;

public class ChangeDataCaptureRuntimeException extends RuntimeException {

    public ChangeDataCaptureRuntimeException(String message) {
        super(message);
    }

    public ChangeDataCaptureRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
