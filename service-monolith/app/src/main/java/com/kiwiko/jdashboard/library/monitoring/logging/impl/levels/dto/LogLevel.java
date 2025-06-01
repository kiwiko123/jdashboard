package com.kiwiko.jdashboard.library.monitoring.logging.impl.levels.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private static final Map<String, LogLevel> levelsByName = Arrays.stream(values())
            .collect(Collectors.toMap(LogLevel::getName, Function.identity()));

    public static Optional<LogLevel> getByName(String name) {
        return Optional.ofNullable(levelsByName.get(name.toLowerCase()));
    }
}
