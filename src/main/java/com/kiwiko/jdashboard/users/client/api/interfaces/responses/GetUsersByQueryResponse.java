package com.kiwiko.jdashboard.users.client.api.interfaces.responses;

import com.kiwiko.jdashboard.users.client.api.dto.User;

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
