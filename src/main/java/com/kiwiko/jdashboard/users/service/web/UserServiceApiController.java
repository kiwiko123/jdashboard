package com.kiwiko.jdashboard.users.service.web;

import com.kiwiko.jdashboard.users.client.api.dto.User;
import com.kiwiko.jdashboard.users.client.api.interfaces.responses.CreateUserInput;
import com.kiwiko.jdashboard.users.client.api.interfaces.responses.CreateUserOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.LockedApi;
import com.kiwiko.jdashboard.users.service.api.interfaces.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@JdashboardConfigured
@RequestMapping("/users/service-api")
@LockedApi
public class UserServiceApiController {

    @Inject private UserService userService;

    @PostMapping("")
    public CreateUserOutput createUser(@RequestBody CreateUserInput input) {
        com.kiwiko.jdashboard.users.service.api.dto.User userToCreate = new com.kiwiko.jdashboard.users.service.api.dto.User();
        userToCreate.setUsername(input.getUsername());
        userToCreate.setEmailAddress(input.getEmailAddress());;

        com.kiwiko.jdashboard.users.service.api.dto.User createdUser = userService.create(userToCreate);
        User user = userService.toUser(createdUser);

        CreateUserOutput output = new CreateUserOutput();
        output.setUser(user);

        return output;
    }
}
