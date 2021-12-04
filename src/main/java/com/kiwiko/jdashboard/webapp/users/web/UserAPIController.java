package com.kiwiko.jdashboard.webapp.users.web;

import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.webapp.framework.security.environments.data.EnvironmentProperties;
import com.kiwiko.jdashboard.webapp.users.api.UserService;
import com.kiwiko.jdashboard.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.webapp.users.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL)
@Controller
public class UserAPIController {

    @Inject private UserService userService;

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
    @AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
    @ResponseBody
    public User mergeUser(
            @PathVariable("userId") Long userId,
            @RequestBody User user) {
        user.setId(userId);
        return userService.merge(user);
    }
}
