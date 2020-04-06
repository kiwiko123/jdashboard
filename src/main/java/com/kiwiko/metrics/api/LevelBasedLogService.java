package com.kiwiko.metrics.api;

import com.kiwiko.metrics.data.LogLevel;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LevelBasedLogService implements LogService {

    protected static final int MAX_STACK_TRACE_LIMIT = 10;

    private final Set<LogLevel> enabledLogLevels;

    public LevelBasedLogService() {
        enabledLogLevels = Arrays.stream(LogLevel.values())
                .collect(Collectors.toSet());
    }

    /**
     * Implementation for what it means to "log" a message.
     * Default implementations are provided for all other required methods.
     *
     * @param message the message to log
     * @param level the severity level of the log
     */
    protected abstract void log(String message, LogLevel level);

    /**
     * By default, this will concatenate the provided message and the exception stacktrace.
     *
     * @see #log(String, LogLevel)
     */
    protected void log(String message, Throwable cause, LogLevel level) {
        String stackTrace = Arrays.stream(cause.getStackTrace())
                .limit(MAX_STACK_TRACE_LIMIT)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
        String formattedMessage = String.format("%s\n%s", message, stackTrace);
        log(formattedMessage, level);
    }

    /**
     * Determines if a log of the given level can be logged.
     * By default, all levels can be logged.
     *
     * @param logLevel the level that can be logged
     * @return true if logs of the given level should be logged, or false if not
     */
    protected boolean shouldLog(LogLevel logLevel) {
        return enabledLogLevels.contains(logLevel);
    }

    protected int getMaxStackTraceLimit() {
        return MAX_STACK_TRACE_LIMIT;
    }

    @Override
    public void debug(String message) {
        logEvent(message, LogLevel.DEBUG);
    }

    @Override
    public void debug(String message, Throwable cause) {
        logEventWithThrowable(message, cause, LogLevel.DEBUG);
    }

    @Override
    public void info(String message) {
        logEvent(message, LogLevel.INFO);
    }

    @Override
    public void info(String message, Throwable cause) {
        logEventWithThrowable(message, cause, LogLevel.INFO);
    }

    @Override
    public void warn(String message) {
        logEvent(message, LogLevel.WARN);
    }

    @Override
    public void warn(String message, Throwable cause) {
        logEventWithThrowable(message, cause, LogLevel.WARN);
    }

    @Override
    public void error(String message) {
        logEvent(message, LogLevel.ERROR);
    }

    @Override
    public void error(String message, Throwable cause) {
        logEventWithThrowable(message, cause, LogLevel.ERROR);
    }

    private void logEvent(String message, LogLevel logLevel) {
        if (shouldLog(logLevel)) {
            log(message, logLevel);
        }
    }

    private void logEventWithThrowable(String message, Throwable cause, LogLevel logLevel) {
        if (shouldLog(logLevel)) {
            log(message, cause, logLevel);
        }
    }
}
