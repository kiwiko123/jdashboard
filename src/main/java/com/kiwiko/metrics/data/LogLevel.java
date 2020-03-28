package com.kiwiko.metrics.data;

public enum LogLevel {

    DEBUG("debug"),
    INFO("info"),
    WARN("warn"),
    ERROR("error");

    private final String name;

    LogLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
