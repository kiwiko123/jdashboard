package com.kiwiko.jdashboard.services.userauth.web;

import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.responses.CreateUserOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import com.kiwiko.jdashboard.services.userauth.api.interfaces.exceptions.CreateUserException;
import com.kiwiko.jdashboard.services.userauth.internal.UserCreator;
import com.kiwiko.jdashboard.services.userauth.internal.UserLoginAuthenticator;
import com.kiwiko.jdashboard.services.userauth.api.interfaces.exceptions.UserAuthenticationException;
import com.kiwiko.jdashboard.services.userauth.web.dto.CreateUserInput;
import com.kiwiko.jdashboard.services.userauth.web.dto.GetCurrentUserResponse;
import com.kiwiko.jdashboard.services.userauth.web.dto.LogUserInInput;
import com.kiwiko.jdashboard.services.userauth.web.dto.UserLoginData;
import com.kiwiko.jdashboard.services.userauth.web.dto.LogUserInOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@RestController
@JdashboardConfigured
@RequestMapping("/user-auth/public-api")
public class UserAuthApiController {

    @Inject private UserLoginAuthenticator userLoginAuthenticator;
    @Inject private UserCreator userCreator;

    @GetMapping("/users/current")
    public GetCurrentUserResponse getCurrentUser(@AuthenticatedUser(required = false) User user) {
        if (user == null) {
            return null;
        }

        GetCurrentUserResponse response = new GetCurrentUserResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        return  response;
    }

    @PostMapping("/users/log-in")
    public LogUserInOutput logUserIn(
            @RequestBody UserLoginData userLoginData,
            HttpServletResponse httpServletResponse) throws UserAuthenticationException {
        LogUserInInput logUserInInput = new LogUserInInput();
        logUserInInput.setUserLoginData(userLoginData);
        logUserInInput.setHttpServletResponse(httpServletResponse);;

        return userLoginAuthenticator.logUserIn(logUserInInput);
    }

    @PostMapping("/users")
    public CreateUserOutput createUser(
            @RequestBody CreateUserInput createUserInput,
            HttpServletResponse httpServletResponse) throws CreateUserException, UserAuthenticationException {
        CreateUserOutput createUserOutput = userCreator.createUser(createUserInput);

        UserLoginData userLoginData = new UserLoginData();
        userLoginData.setUsername(createUserOutput.getUser().getUsername());
        userLoginData.setPassword(createUserInput.getPassword());

        LogUserInInput logUserInInput = new LogUserInInput();
        logUserInInput.setUserLoginData(userLoginData);
        logUserInInput.setHttpServletResponse(httpServletResponse);
        userLoginAuthenticator.logUserIn(logUserInInput);

        return createUserOutput;
    }
}
