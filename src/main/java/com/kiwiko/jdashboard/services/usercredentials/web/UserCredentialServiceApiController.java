package com.kiwiko.jdashboard.services.usercredentials.web;

import com.kiwiko.jdashboard.clients.usercredentials.api.dto.UserCredential;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialOutput;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks.InternalServiceCheck;
import com.kiwiko.jdashboard.services.usercredentials.api.interfaces.UserCredentialService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@JdashboardConfigured
@RequestMapping("/user-credentials/service-api")
@InternalServiceCheck
public class UserCredentialServiceApiController {

    @Inject private UserCredentialService userCredentialService;

    @PostMapping("")
    public CreateUserCredentialOutput createUserCredential(@RequestBody CreateUserCredentialInput input) {
        UserCredential userCredential = userCredentialService.create(input);

        CreateUserCredentialOutput output = new CreateUserCredentialOutput();
        output.setUserCredential(userCredential);

        return output;
    }
}
