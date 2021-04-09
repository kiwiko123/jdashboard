package com.kiwiko.webapp.users.web;

import com.google.gson.JsonSyntaxException;
import com.kiwiko.webapp.mvc.json.api.JsonSerializer;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.webapp.users.data.User;
import com.kiwiko.clients.users.api.parameters.GetUsersQuery;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL)
@RestController
public class UserAPIController {

    @Inject private UserService userService;
    @Inject private JsonSerializer jsonSerializer;

    @GetMapping("/users/api/{userId}")
    public ResponsePayload getUser(@PathVariable(name = "userId") long userId) {
        User user = userService.getById(userId).orElse(null);

        return new ResponseBuilder()
                .withBody(user)
                .build();
    }

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
}
