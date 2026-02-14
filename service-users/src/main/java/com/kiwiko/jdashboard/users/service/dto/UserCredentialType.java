package com.kiwiko.jdashboard.users.service.dto;

public enum UserCredentialType {
    PASSWORD("password");

    private final String id;

    UserCredentialType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
