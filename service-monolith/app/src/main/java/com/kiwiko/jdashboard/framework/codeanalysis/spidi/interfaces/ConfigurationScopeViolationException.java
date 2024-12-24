package com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces;

public class ConfigurationScopeViolationException extends SpiDiException {
    public ConfigurationScopeViolationException(String message) {
        super(message);
    }

    public ConfigurationScopeViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
