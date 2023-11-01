package com.kiwiko.jdashboard.library.monitoring.logging.impl.console;

import com.kiwiko.jdashboard.library.monitoring.logging.impl.levels.LevelBasedLogger;
import com.kiwiko.jdashboard.library.monitoring.logging.impl.levels.dto.LevelBasedLog;

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
