package com.kiwiko.jdashboard.webapp.mvc.security.environments.data;

public enum EnvironmentType {
    LOCAL("local"),
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
