package com.kiwiko.jdashboard.webapp.persistence.data.cdc.api.interfaces.exceptions;

public class EntityChangeDataCaptureException extends ChangeDataCaptureRuntimeException {

    public EntityChangeDataCaptureException(String message) {
        super(message);
    }

    public EntityChangeDataCaptureException(String message, Throwable cause) {
        super(message, cause);
    }
}
