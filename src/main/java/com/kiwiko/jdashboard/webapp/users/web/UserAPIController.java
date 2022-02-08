package com.kiwiko.jdashboard.webapp.users.web;

import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.checks.UserAuthCheck;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.checks.InternalServiceCheck;
import com.kiwiko.jdashboard.webapp.framework.security.environments.data.EnvironmentProperties;
import com.kiwiko.jdashboard.webapp.users.api.UserService;
import com.kiwiko.jdashboard.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.webapp.users.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL)
@Controller
public class UserAPIController {

    @Inject private UserService userService;
    @Inject private GsonProvider gsonProvider;

    @GetMapping("/users/api/{userId}")
    @ResponseBody
    public User getUser(@PathVariable(name = "userId") long userId) {
        return userService.getById(userId).orElse(null);
    }

    @PostMapping("/users/api")
    public ResponsePayload createUser(@RequestBody CreateUserParameters parameters) {
        User result = userService.create(parameters);
        return new ResponseBuilder()
                .withBody(result)
                .build();
    }

    @PatchMapping("/users/api/{userId}")
    @UserAuthCheck
    @ResponseBody
    public User mergeUser(
            @PathVariable("userId") Long userId,
            @RequestBody User user) {
        user.setId(userId);
        return userService.merge(user);
    }

    @GetMapping("/users/api/internal/query")
    @InternalServiceCheck
    @ResponseBody
    public GetUsersByQueryResponse getUsersByQuery(
            @RequestParam("query") String queryJson) {
        String decodedQuery = URLDecoder.decode(queryJson, StandardCharsets.UTF_8);
        GetUsersQuery getUsersQuery = gsonProvider.getDefault().fromJson(decodedQuery, GetUsersQuery.class);
        return userService.getByQuery(getUsersQuery);
    }
}
