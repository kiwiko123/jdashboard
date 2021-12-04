package com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses;

import com.kiwiko.jdashboard.webapp.clients.users.api.dto.User;

import java.util.Collections;
import java.util.Set;

public class GetUsersByQueryResponse {

    private Set<User> users = Collections.emptySet();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
