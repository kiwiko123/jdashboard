package com.kiwiko.mvc.metrics.api;

public interface LogService {

    void debug(String message);
    void debug(String message, Throwable cause);

    void info(String message);
    void info(String message, Throwable cause);

    void warn(String message);
    void warn(String message, Throwable cause);

    void error(String message);
    void error(String message, Throwable cause);
}
