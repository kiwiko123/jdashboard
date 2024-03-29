package com.kiwiko.jdashboard.webapp.framework.security.authentication.web;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.UserCredentialClient;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.EncryptionStrategies;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks.UserAuthCheck;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticatedUser;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.dto.UserLoginParameters;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.events.UserAuthenticationEventClient;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionService;
import com.kiwiko.jdashboard.clients.sessions.api.dto.Session;
import com.kiwiko.jdashboard.services.users.api.interfaces.UserService;
import com.kiwiko.jdashboard.services.users.api.interfaces.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.services.users.api.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@JdashboardConfigured
@RestController
public class LegacyUserAuthenticationAPIController {

    @Inject private SessionService sessionService;
    @Inject private UserService userService;
    @Inject private UserAuthenticationEventClient userAuthenticationEventClient;
    @Inject private UserCredentialClient userCredentialClient;
    @Inject private Logger logger;

    @PostMapping("/user-auth/api/create")
    public ResponsePayload createUser(
            @RequestBody CreateUserParameters createUserParameters,
            HttpServletResponse httpServletResponse) {
        User result = userService.create(createUserParameters);
        Session session = sessionService.createSessionCookieForUser(result.getId(), httpServletResponse);

        CreateUserCredentialInput createUserCredentialInput = new CreateUserCredentialInput(result.getId(), "user_password", createUserParameters.getPassword());
        createUserCredentialInput.setEncryptionStrategy(EncryptionStrategies.DEFAULT);
        userCredentialClient.create(createUserCredentialInput);

        return new ResponseBuilder()
                .withBody(session)
                .build();
    }

    @PostMapping("/user-auth/api/login")
    public ResponsePayload login(
            @RequestBody UserLoginParameters userLoginParameters,
            HttpServletResponse httpServletResponse) {
        User user = userService.getByLoginParameters(userLoginParameters)
                .orElse(null);
        if (user == null) {
            return getInvalidUserResponse();
        }

        sessionService.createSessionCookieForUser(user.getId(), httpServletResponse);
        userAuthenticationEventClient.recordLogInEvent(user.getId());

        return new ResponseBuilder()
                .withBody(user)
                .build();
    }

    @UserAuthCheck
    @PostMapping("/user-auth/api/users/current/logout")
    public ResponsePayload logCurrentUserOut(@AuthenticatedUser com.kiwiko.jdashboard.clients.users.api.dto.User user) {
        sessionService.endSessionForUser(user.getId());
        userAuthenticationEventClient.recordLogOutEvent(user.getId());

        return ResponseBuilder.ok();
    }

    @GetMapping("/user-auth/api/users/current")
    public ResponsePayload getCurrentUser(@AuthenticatedUser(required = false) com.kiwiko.jdashboard.clients.users.api.dto.User currentUser) {
        return new ResponseBuilder()
                .withBody(currentUser)
                .build();
    }

    private ResponsePayload getInvalidUserResponse() {
        return new ResponseBuilder()
                .withError("No user found with those credentials")
                .build();
    }
}
