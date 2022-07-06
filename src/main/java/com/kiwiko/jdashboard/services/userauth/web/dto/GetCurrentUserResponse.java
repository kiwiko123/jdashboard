package com.kiwiko.jdashboard.services.userauth.web.dto;

public class GetCurrentUserResponse {
    private long userId;
    private String username;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
