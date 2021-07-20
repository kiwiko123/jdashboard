package com.kiwiko.webapp.mvc.security.authentication.web;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.requests.api.annotations.RequestBodyParameter;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.webapp.mvc.security.authentication.api.dto.UserLoginParameters;
import com.kiwiko.webapp.mvc.security.authentication.internal.events.UserAuthenticationEventClient;
import com.kiwiko.webapp.mvc.security.sessions.api.SessionService;
import com.kiwiko.webapp.mvc.security.sessions.data.Session;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.api.parameters.CreateUserParameters;
import com.kiwiko.webapp.users.data.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@CrossOriginConfigured
@RestController
public class UserAuthenticationAPIController {

    @Inject private SessionService sessionService;
    @Inject private UserService userService;
    @Inject private UserAuthenticationEventClient userAuthenticationEventClient;
    @Inject private LogService logService;

    @PostMapping("/user-auth/api/create")
    public ResponsePayload createUser(
            @RequestBody CreateUserParameters createUserParameters,
            HttpServletResponse httpServletResponse) {
        User result = userService.create(createUserParameters);
        Session session = sessionService.createSessionCookieForUser(result.getId(), httpServletResponse);

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

    @PostMapping("/user-auth/api/users/current/logout")
    public ResponsePayload logCurrentUserOut(RequestContext requestContext) {
        User user = requestContext.getUser()
                .orElse(null);
        if (user == null) {
            return new ResponseBuilder()
                    .withError("Please try refreshing the page")
                    .build();
        }

        sessionService.endSessionForUser(user.getId());
        userAuthenticationEventClient.recordLogOutEvent(user.getId());

        return ResponseBuilder.ok();
    }

    @GetMapping("/user-auth/api/users/current")
    public ResponsePayload getCurrentUser(RequestContext requestContext) {
        User currentUser = requestContext.getUser().orElse(null);
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
