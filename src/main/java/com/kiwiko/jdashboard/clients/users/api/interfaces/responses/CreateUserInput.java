package com.kiwiko.jdashboard.clients.users.api.interfaces.responses;

import javax.annotation.Nullable;

public class CreateUserInput {
    private String username;
    private @Nullable String emailAddress;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(@Nullable String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
