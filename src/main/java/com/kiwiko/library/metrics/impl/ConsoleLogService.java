package com.kiwiko.library.metrics.impl;

import com.kiwiko.library.metrics.api.LevelBasedLogService;
import com.kiwiko.library.metrics.data.LevelBasedLog;

public class ConsoleLogService extends LevelBasedLogService {

    @Override
    protected void log(LevelBasedLog log) {
        System.out.println(String.format("[%s] %s", log.getLevel().getName().toUpperCase(), log.getMessage()));
    }

    @Override
    protected void logEvent(LevelBasedLog log) {
        log(log);
        log.getException().ifPresent(Throwable::printStackTrace);
    }
}
