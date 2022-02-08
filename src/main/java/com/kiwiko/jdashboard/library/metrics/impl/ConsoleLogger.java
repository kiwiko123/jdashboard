package com.kiwiko.jdashboard.library.metrics.impl;

import com.kiwiko.jdashboard.library.metrics.api.LevelBasedLogger;
import com.kiwiko.jdashboard.library.metrics.data.LevelBasedLog;

public class ConsoleLogger extends LevelBasedLogger {

    @Override
    protected void log(LevelBasedLog log) {
        System.out.printf("[%s] %s%n", log.getLevel().getName().toUpperCase(), log.getMessage());
    }

    @Override
    protected void logEvent(LevelBasedLog log) {
        log(log);
        log.getException().ifPresent(Throwable::printStackTrace);
    }
}
