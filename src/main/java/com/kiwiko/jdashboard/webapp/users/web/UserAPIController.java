package com.kiwiko.jdashboard.webapp.users.web;

import com.google.gson.JsonSyntaxException;
import com.kiwiko.jdashboard.webapp.framework.json.api.JsonSerializer;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.webapp.framework.security.environments.data.EnvironmentProperties;
import com.kiwiko.jdashboard.webapp.users.api.UserService;
import com.kiwiko.jdashboard.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.webapp.users.data.User;
import com.kiwiko.jdashboard.webapp.clients.users.api.parameters.GetUsersQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL)
@Controller
public class UserAPIController {

    @Inject private UserService userService;
    @Inject private JsonSerializer jsonSerializer;

    @GetMapping("/users/api/{userId}")
    @ResponseBody
    public User getUser(@PathVariable(name = "userId") long userId) {
        return userService.getById(userId).orElse(null);
    }

    @AuthenticationRequired(levels = AuthenticationLevel.INTERNAL_SERVICE)
    @GetMapping("/users/api")
    public ResponsePayload getUsers(@RequestParam("query") String jsonQuery) {
        GetUsersQuery query;
        try {
            query = jsonSerializer.fromJson(jsonQuery, GetUsersQuery.class);
        } catch (JsonSyntaxException e) {
            return new ResponseBuilder()
                    .withError(String.format("Failed to deserialize \"%s\"", jsonQuery))
                    .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        List<User> users = userService.getByQuery(query);
        return ResponseBuilder.payload(users);
    }

    @PostMapping("/users/api")
    public ResponsePayload createUser(@RequestBody CreateUserParameters parameters) {
        User result = userService.create(parameters);
        return new ResponseBuilder()
                .withBody(result)
                .build();
    }

    @PatchMapping("/users/api/{userId}")
    @AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
    @ResponseBody
    public User mergeUser(
            @PathVariable("userId") Long userId,
            @RequestBody User user) {
        user.setId(userId);
        return userService.merge(user);
    }
}
