package com.kiwiko.jdashboard.featureflags.service.api.interfaces;

public class FeatureFlagRuntimeException extends RuntimeException {
    public FeatureFlagRuntimeException(String message) {
        super(message);
    }

    public FeatureFlagRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
