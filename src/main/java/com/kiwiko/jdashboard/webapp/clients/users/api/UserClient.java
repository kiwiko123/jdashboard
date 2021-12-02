package com.kiwiko.jdashboard.webapp.clients.users.api;

import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.users.data.User;

import java.util.List;

public interface UserClient {

    List<User> getByQuery(GetUsersQuery query) throws InterruptedException;
}
