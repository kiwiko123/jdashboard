package com.kiwiko.library.metrics.impl;

import com.kiwiko.library.metrics.api.LevelBasedLogger;
import com.kiwiko.library.metrics.data.LevelBasedLog;

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
