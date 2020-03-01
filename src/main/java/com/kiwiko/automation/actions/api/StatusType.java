package com.kiwiko.automation.actions.api;

public enum StatusType {
    WAITING("waiting"),
    RUNNING("running"),
    COMPLETE("complete"),
    FAILED("failed");

    private final String id;

    StatusType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isFinished() {
        return this == COMPLETE || this == FAILED;
    }
}
