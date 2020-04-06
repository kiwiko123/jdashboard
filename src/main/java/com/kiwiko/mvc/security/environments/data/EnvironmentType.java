package com.kiwiko.mvc.security.environments.data;

public enum EnvironmentType {
    TEST("test"),
    PRODUCTION("production");

    private final String id;

    EnvironmentType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}