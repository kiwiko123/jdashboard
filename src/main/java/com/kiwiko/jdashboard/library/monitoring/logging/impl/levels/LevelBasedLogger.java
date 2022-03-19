package com.kiwiko.jdashboard.library.monitoring.logging.impl.levels;

import com.kiwiko.jdashboard.library.monitoring.logging.impl.levels.dto.LevelBasedLog;
import com.kiwiko.jdashboard.library.monitoring.logging.api.dto.Log;
import com.kiwiko.jdashboard.library.monitoring.logging.impl.levels.dto.LogLevel;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.library.strings.StringSubstitutor;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public abstract class LevelBasedLogger implements Logger {

    protected int MAX_STACK_TRACE_LIMIT = 10;

    private final Set<LogLevel> enabledLogLevels;
    private final StringSubstitutor stringSubstitutor;

    public LevelBasedLogger() {
        enabledLogLevels = EnumSet.allOf(LogLevel.class);
        stringSubstitutor = new StringSubstitutor();
    }

    /**
     * Implementation for what it means to "log" a message.
     * Default implementations are provided for all other required methods.
     *
     * @param log
     */
    protected abstract void log(LevelBasedLog log);

    /**
     * By default, this will concatenate the provided message and the exception stacktrace.
     *
     * @see #log(LevelBasedLog)
     */
    protected void logEvent(LevelBasedLog log) {
        LevelBasedLog copy = new LevelBasedLog(log);
        String message = log.getMessage();

        if (copy.getException().isPresent()) {
            Throwable throwable = copy.getException().get();
            String stackTrace = Arrays.stream(copy.getException().get().getStackTrace())
                    .limit(MAX_STACK_TRACE_LIMIT)
                    .map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n"));
            message = String.format("%s\n%s", message, stackTrace);
        }

        copy.setMessage(message);
        log(copy);
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

    @Override
    public void debug(Log log) {
        LevelBasedLog copy = new LevelBasedLog(log, LogLevel.DEBUG);
        logEventWithFilter(copy);
    }

    @Override
    public void debug(String message) {
        LevelBasedLog log = new LevelBasedLog();
        log.setLevel(LogLevel.DEBUG);
        log.setMessage(message);

        logEventWithFilter(log);
    }

    @Override
    public void debug(String message, Throwable cause) {
        LevelBasedLog log = new LevelBasedLog();
        log.setLevel(LogLevel.DEBUG);
        log.setMessage(message);
        log.setException(cause);

        logEventWithFilter(log);
    }

    @Override
    public void debug(String template, Object... args) {
        logTemplate(this::debug, template, args);
    }

    @Override
    public void info(Log log) {
        LevelBasedLog copy = new LevelBasedLog(log, LogLevel.INFO);
        logEventWithFilter(copy);
    }

    @Override
    public void info(String message) {
        LevelBasedLog log = new LevelBasedLog();
        log.setLevel(LogLevel.INFO);
        log.setMessage(message);

        logEventWithFilter(log);
    }

    @Override
    public void info(String message, Throwable cause) {
        LevelBasedLog log = new LevelBasedLog();
        log.setLevel(LogLevel.INFO);
        log.setMessage(message);
        log.setException(cause);

        logEventWithFilter(log);
    }

    @Override
    public void info(String template, Object... args) {
        logTemplate(this::info, template, args);
    }

    @Override
    public void warn(Log log) {
        LevelBasedLog copy = new LevelBasedLog(log, LogLevel.WARN);
        logEventWithFilter(copy);
    }

    @Override
    public void warn(String message) {
        LevelBasedLog log = new LevelBasedLog();
        log.setLevel(LogLevel.WARN);
        log.setMessage(message);

        logEventWithFilter(log);
    }

    @Override
    public void warn(String message, Throwable cause) {
        LevelBasedLog log = new LevelBasedLog();
        log.setLevel(LogLevel.WARN);
        log.setMessage(message);
        log.setException(cause);

        logEventWithFilter(log);
    }

    @Override
    public void warn(String template, Object... args) {
        logTemplate(this::warn, template, args);
    }

    @Override
    public void error(Log log) {
        LevelBasedLog copy = new LevelBasedLog(log, LogLevel.ERROR);
        logEventWithFilter(copy);
    }

    @Override
    public void error(String message) {
        LevelBasedLog log = new LevelBasedLog();
        log.setLevel(LogLevel.ERROR);
        log.setMessage(message);

        logEventWithFilter(log);
    }

    @Override
    public void error(String message, Throwable cause) {
        LevelBasedLog log = new LevelBasedLog();
        log.setLevel(LogLevel.ERROR);
        log.setMessage(message);
        log.setException(cause);

        logEventWithFilter(log);
    }

    @Override
    public void error(String template, Object... args) {
        logTemplate(this::error, template, args);
    }

    private void logEventWithFilter(LevelBasedLog log) {
        if (shouldLog(log.getLevel())) {
            logEvent(log);
        }
    }

    private void logTemplate(BiConsumer<String, Throwable> logImpl, String template, Object... args) {
        Object lastArg = args[args.length - 1];
        Object[] argsToSubstitute;

        Throwable throwable = null;
        if (lastArg instanceof Throwable) {
            throwable = (Throwable) lastArg;
            argsToSubstitute = Arrays.copyOf(args, args.length - 1);
        } else {
            argsToSubstitute = args;
        }

        String logMessage = stringSubstitutor.substitute(template, "{}", argsToSubstitute);
        logImpl.accept(logMessage, throwable);
    }
}
