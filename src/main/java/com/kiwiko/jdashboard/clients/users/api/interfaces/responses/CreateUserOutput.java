package com.kiwiko.jdashboard.clients.users.api.interfaces.responses;

import com.kiwiko.jdashboard.clients.users.api.dto.User;

public class CreateUserOutput {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
