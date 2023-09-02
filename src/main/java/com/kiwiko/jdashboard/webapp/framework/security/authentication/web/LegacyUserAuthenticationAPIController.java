package com.kiwiko.jdashboard.webapp.framework.security.authentication.web;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.UserCredentialClient;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.EncryptionStrategies;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserAuthCheck;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.sessions.service.api.interfaces.SessionService;
import com.kiwiko.jdashboard.sessions.client.api.dto.Session;
import com.kiwiko.jdashboard.services.users.api.interfaces.UserService;
import com.kiwiko.jdashboard.services.users.api.interfaces.parameters.CreateUserParameters;
import com.kiwiko.jdashboard.services.users.api.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/**
 * @deprecated Prefer {@link com.kiwiko.jdashboard.userauth.service.web.UserAuthApiController}
 */
@JdashboardConfigured
@RestController
@Deprecated
public class LegacyUserAuthenticationAPIController {

    @Inject private SessionService sessionService;
    @Inject private UserService userService;
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

    @UserAuthCheck
    @PostMapping("/user-auth/api/users/current/logout")
    public ResponsePayload logCurrentUserOut(@AuthenticatedUser com.kiwiko.jdashboard.clients.users.api.dto.User user) {
        sessionService.endSessionForUser(user.getId());

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
