package com.kiwiko.mvc.metrics.internal;

import com.kiwiko.mvc.metrics.api.LogService;

public class ConsoleLogService implements LogService {

    @Override
    public void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    @Override
    public void debug(String message, Throwable cause) {
        log(message, cause, LogLevel.DEBUG);
    }

    @Override
    public void info(String message) {
        log(message, LogLevel.INFO);
    }

    @Override
    public void info(String message, Throwable cause) {
        log(message, cause, LogLevel.INFO);
    }

    @Override
    public void warn(String message) {
        log(message, LogLevel.WARN);
    }

    @Override
    public void warn(String message, Throwable cause) {
        log(message, cause, LogLevel.WARN);
    }

    @Override
    public void error(String message) {
        log(message, LogLevel.ERROR);
    }

    @Override
    public void error(String message, Throwable cause) {
        log(message, cause, LogLevel.ERROR);
    }

    private void log(String message, LogLevel level) {
        System.out.println(String.format("[%s] %s", level.getId().toUpperCase(), message));
    }

    private void log(String message, Throwable cause, LogLevel level) {
        log(message, level);
        cause.printStackTrace();
    }
}
