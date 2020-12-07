package com.kiwiko.webapp.users.web;

import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.WebResponse;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.webapp.users.data.User;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL)
@RestController
public class UserAPIController {

    @Inject
    private UserService userService;

    @GetMapping("/users/api/{userId}")
    public WebResponse getUser(@PathVariable(name = "userId") long userId) {
        User user = userService.getById(userId).orElse(null);

        return new ResponseBuilder()
                .withBody(user)
                .build();
    }

    @PostMapping("/users/api")
    public WebResponse createUser(@RequestBody CreateUserParameters parameters) {
        User result = userService.create(parameters);
        return new ResponseBuilder()
                .withBody(result)
                .build();
    }
}
