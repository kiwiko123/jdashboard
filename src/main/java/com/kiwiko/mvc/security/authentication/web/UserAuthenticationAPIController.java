package com.kiwiko.mvc.security.authentication.web;

import com.kiwiko.mvc.json.data.ResponseBuilder;
import com.kiwiko.mvc.json.data.ResponsePayload;
import com.kiwiko.mvc.requests.api.RequestBodyParameter;
import com.kiwiko.mvc.requests.data.RequestContext;
import com.kiwiko.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.mvc.security.environments.data.EnvironmentProperties;
import com.kiwiko.mvc.security.sessions.api.SessionService;
import com.kiwiko.mvc.security.sessions.data.Session;
import com.kiwiko.users.api.UserService;
import com.kiwiko.users.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@CrossOriginConfigured
@RestController
public class UserAuthenticationAPIController {

    @Inject
    private SessionService sessionService;

    @Inject
    private UserService userService;

    @PostMapping("/user-auth/api/create")
    public ResponseEntity<ResponsePayload> createUser(
            @RequestBody User user,
            HttpServletResponse httpServletResponse) {
        User result = userService.create(user);
        Session session = sessionService.createSessionCookieForUser(result.getId(), httpServletResponse);

        return new ResponseBuilder()
                .withBody(session)
                .toResponseEntity();
    }

    @PostMapping("/user-auth/api/login")
    public ResponseEntity<ResponsePayload> login(
            @RequestBodyParameter(name = "username") String username,
            @RequestBodyParameter(name = "password") String password,
            HttpServletResponse httpServletResponse) {
        User user = userService.getWithValidation(username, password)
                .orElse(null);
        if (user == null) {
            return getInvalidUserResponse(username);
        }

        sessionService.createSessionCookieForUser(user.getId(), httpServletResponse);

        return new ResponseBuilder()
                .withBody(user)
                .toResponseEntity();
    }

    @GetMapping("/user-auth/api/get-current-user")
    public ResponseEntity<ResponsePayload> getCurrentUser(RequestContext requestContext) {
        User currentUser = requestContext.getUser()
                .orElse(null);

        if (currentUser == null) {
            return new ResponseBuilder()
                    .withError("No logged-in user found")
                    .toResponseEntity();
        }

        return new ResponseBuilder()
                .withBody(currentUser)
                .toResponseEntity();
    }

    private ResponseEntity<ResponsePayload> getInvalidUserResponse(String username) {
        return new ResponseBuilder()
                .withError(String.format("No user found with username \"%s\"", username))
                .toResponseEntity();
    }
}
