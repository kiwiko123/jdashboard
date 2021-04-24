package com.kiwiko.webapp.clients.users.api;

import com.kiwiko.webapp.clients.users.api.parameters.GetUsersQuery;
import com.kiwiko.webapp.users.data.User;

import java.util.List;

public interface UserClient {

    List<User> getByQuery(GetUsersQuery query) throws InterruptedException;
}
