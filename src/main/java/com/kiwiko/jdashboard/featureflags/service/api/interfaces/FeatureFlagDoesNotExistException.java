package com.kiwiko.jdashboard.featureflags.service.api.interfaces;

public class FeatureFlagDoesNotExistException extends RuntimeException {
    public FeatureFlagDoesNotExistException(String message) {
        super(message);
    }

    public FeatureFlagDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
