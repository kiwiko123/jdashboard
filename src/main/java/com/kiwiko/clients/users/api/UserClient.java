package com.kiwiko.clients.users.api;

import com.kiwiko.clients.users.api.parameters.GetUsersQuery;
import com.kiwiko.webapp.users.data.User;

import java.util.List;

public interface UserClient {

    List<User> getByQuery(GetUsersQuery query) throws InterruptedException;
}
