package com.kiwiko.jdashboard.framework.monitoring.logging.impl;

import com.kiwiko.jdashboard.library.monitoring.logging.api.dto.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a stop-gap implementation of the legacy {@link com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger}
 * powered by {@link Logger}. This is however not ideal when compared to using {@link Logger} directly because it
 * does not show the class invoking the logger.
 */
public class StopGapSlf4jLogger implements com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger {
    private static final Logger LOGGER = LoggerFactory.getLogger(StopGapSlf4jLogger.class);

    @Override
    public void debug(Log log) {
        if (log.getException().isPresent()) {
            LOGGER.debug(log.getMessage(), log.getException().get());
        } else {
            LOGGER.debug(log.getMessage());
        }
    }

    @Override
    public void debug(String message) {
        LOGGER.debug(message);
    }

    @Override
    public void debug(String message, Throwable cause) {
        LOGGER.debug(message, cause);
    }

    @Override
    public void debug(String template, Object... args) {
        LOGGER.debug(template, args);
    }

    @Override
    public void info(Log log) {
        if (log.getException().isPresent()) {
            LOGGER.info(log.getMessage(), log.getException().get());
        } else {
            LOGGER.info(log.getMessage());
        }
    }

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void info(String message, Throwable cause) {
        LOGGER.info(message, cause);
    }

    @Override
    public void info(String template, Object... args) {
        LOGGER.info(template, args);
    }

    @Override
    public void warn(Log log) {
        if (log.getException().isPresent()) {
            LOGGER.warn(log.getMessage(), log.getException().get());
        } else {
            LOGGER.warn(log.getMessage());
        }
    }

    @Override
    public void warn(String message) {
        LOGGER.warn(message);
    }

    @Override
    public void warn(String message, Throwable cause) {
        LOGGER.warn(message, cause);
    }

    @Override
    public void warn(String template, Object... args) {
        LOGGER.warn(template, args);
    }

    @Override
    public void error(Log log) {
        if (log.getException().isPresent()) {
            LOGGER.error(log.getMessage(), log.getException().get());
        } else {
            LOGGER.error(log.getMessage());
        }
    }

    @Override
    public void error(String message) {
        LOGGER.error(message);
    }

    @Override
    public void error(String message, Throwable cause) {
        LOGGER.error(message, cause);
    }

    @Override
    public void error(String template, Object... args) {
        LOGGER.error(template, args);
    }
}
