package com.kiwiko.webapp.users.api.parameters;

import javax.annotation.Nullable;

public class CreateUserParameters {

    private String username;
    private String password;
    private @Nullable String emailAddress;

    public String getUsername() {
        return username;
    }

    public CreateUserParameters withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CreateUserParameters withPassword(String password) {
        this.password = password;
        return this;
    }

    @Nullable
    public String getEmailAddress() {
        return emailAddress;
    }

    public CreateUserParameters withEmailAddress(@Nullable String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }
}
