package com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces;

public class SpiDiException extends Exception {
    public SpiDiException(String message) {
        super(message);
    }

    public SpiDiException(String message, Throwable cause) {
        super(message, cause);
    }
}
