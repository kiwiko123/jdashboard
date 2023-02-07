package com.kiwiko.jdashboard.services.users.web;

import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.CreateUserInput;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.CreateUserOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.AuthorizedServiceClients;
import com.kiwiko.jdashboard.services.users.api.interfaces.UserService;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardServiceClientIdentifiers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@JdashboardConfigured
@RequestMapping("/users/service-api")
@AuthorizedServiceClients(JdashboardServiceClientIdentifiers.DEFAULT)
public class UserServiceApiController {

    @Inject private UserService userService;

    @PostMapping("")
    public CreateUserOutput createUser(@RequestBody CreateUserInput input) {
        com.kiwiko.jdashboard.services.users.api.dto.User userToCreate = new com.kiwiko.jdashboard.services.users.api.dto.User();
        userToCreate.setUsername(input.getUsername());
        userToCreate.setEmailAddress(input.getEmailAddress());;

        com.kiwiko.jdashboard.services.users.api.dto.User createdUser = userService.create(userToCreate);
        User user = userService.toUser(createdUser);

        CreateUserOutput output = new CreateUserOutput();
        output.setUser(user);

        return output;
    }
}
