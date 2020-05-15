package com.kiwiko.metrics.impl;

import com.kiwiko.metrics.api.LevelBasedLogService;
import com.kiwiko.metrics.data.LevelBasedLog;

public class ConsoleLogService extends LevelBasedLogService {

    @Override
    protected void log(LevelBasedLog log) {
        System.out.println(String.format("[%s] %s", log.getLevel().getName().toUpperCase(), log.getMessage()));
    }
}
