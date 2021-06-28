package com.kiwiko.library.metrics.data;

import com.kiwiko.library.monitoring.logging.api.dto.Log;

public class LevelBasedLog extends Log {

    private LogLevel level;

    public LevelBasedLog() {
        super();
    }

    public LevelBasedLog(LevelBasedLog other) {
        super(other);
        level = other.level;
    }

    public LevelBasedLog(Log log, LogLevel level) {
        super(log);
        this.level = level;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public Log withLevel(LogLevel level) {
        this.level = level;
        return this;
    }
}
