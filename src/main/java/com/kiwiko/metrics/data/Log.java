package com.kiwiko.metrics.data;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

public class Log {

    private String message;
    private @Nullable Throwable exception;
    private Instant timestamp = Instant.now();

    public Log() { }

    public Log(Log other) {
        message = other.message;
        exception = other.exception;
        timestamp = other.timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<Throwable> getException() {
        return Optional.ofNullable(exception);
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
