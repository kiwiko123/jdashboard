package com.kiwiko.metrics.impl;

import com.kiwiko.metrics.api.LevelBasedLogService;
import com.kiwiko.metrics.data.LogLevel;

public class ConsoleLogService extends LevelBasedLogService {

    @Override
    protected void log(String message, LogLevel level) {
        System.out.println(String.format("[%s] %s", level.getName().toUpperCase(), message));
    }

    @Override
    protected void log(String message, Throwable cause, LogLevel level) {
        log(message, level);
        cause.printStackTrace();
    }
}
