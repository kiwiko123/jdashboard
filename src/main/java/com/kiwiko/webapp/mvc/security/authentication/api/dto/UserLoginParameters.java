package com.kiwiko.webapp.mvc.security.authentication.api.dto;

public class UserLoginParameters {
    private String username;
    private String password; // Raw/plaintext

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
