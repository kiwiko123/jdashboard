package com.kiwiko.mvc.metrics.internal;

public enum LogLevel {

    DEBUG("debug"),
    INFO("info"),
    WARN("warn"),
    ERROR("error");

    private final String id;

    LogLevel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
