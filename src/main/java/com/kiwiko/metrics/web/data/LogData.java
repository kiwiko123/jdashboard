package com.kiwiko.metrics.web.data;

import com.kiwiko.metrics.data.LogLevel;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class LogData {

    private String message;
    private String level;
    private @Nullable String error;
    private Instant timestamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogLevel getLevel() {
        return LogLevel.getByName(level)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid level %s", level)));
    }

    public void setLevel(LogLevel level) {
        this.level = level.toString();
    }

    public Optional<String> getError() {
        return Optional.ofNullable(error);
    }

    public void setError(@Nullable String error) {
        this.error = error;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
