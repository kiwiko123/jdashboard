package com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces;

public class DependencyViolationException extends SpiDiException {
    public DependencyViolationException(String message) {
        super(message);
    }

    public DependencyViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
