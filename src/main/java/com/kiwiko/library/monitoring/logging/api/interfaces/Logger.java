package com.kiwiko.library.monitoring.logging.api.interfaces;

import com.kiwiko.library.monitoring.logging.api.dto.Log;

public interface Logger {

    void debug(Log log);
    void debug(String message);
    void debug(String message, Throwable cause);

    void info(Log log);
    void info(String message);
    void info(String message, Throwable cause);

    void warn(Log log);
    void warn(String message);
    void warn(String message, Throwable cause);

    void error(Log log);
    void error(String message);
    void error(String message, Throwable cause);
}
