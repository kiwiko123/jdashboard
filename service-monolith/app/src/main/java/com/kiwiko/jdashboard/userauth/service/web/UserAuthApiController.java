package com.kiwiko.jdashboard.userauth.service.web;

import com.kiwiko.jdashboard.permissions.client.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.parameters.QueryPermissionsOutput;
import com.kiwiko.jdashboard.permissions.service.api.dto.Permission;
import com.kiwiko.jdashboard.users.client.api.dto.User;
import com.kiwiko.jdashboard.users.client.api.interfaces.responses.CreateUserOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import com.kiwiko.jdashboard.userauth.service.api.interfaces.exceptions.CreateUserException;
import com.kiwiko.jdashboard.userauth.service.internal.UserCreator;
import com.kiwiko.jdashboard.userauth.service.internal.UserLoginAuthenticator;
import com.kiwiko.jdashboard.userauth.service.api.interfaces.exceptions.UserAuthenticationException;
import com.kiwiko.jdashboard.userauth.service.web.dto.CreateUserInput;
import com.kiwiko.jdashboard.userauth.service.web.dto.GetCurrentUserResponse;
import com.kiwiko.jdashboard.userauth.service.web.dto.LogUserInInput;
import com.kiwiko.jdashboard.userauth.service.web.dto.UserLoginData;
import com.kiwiko.jdashboard.userauth.service.web.dto.LogUserInOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@JdashboardConfigured
@RequestMapping("/user-auth/public-api")
public class UserAuthApiController {

    @Inject private UserLoginAuthenticator userLoginAuthenticator;
    @Inject private UserCreator userCreator;
    @Inject private PermissionClient permissionClient;

    @GetMapping("/users/current")
    public GetCurrentUserResponse getCurrentUser(@AuthenticatedUser(required = false) @Nullable User user) {
        if (user == null) {
            return null;
        }

        QueryPermissionsInput queryPermissionsInput = QueryPermissionsInput.newBuilder()
                .setUserIds(Collections.singleton(user.getId()))
                .build();
        QueryPermissionsOutput queryPermissionsOutput = permissionClient.query(queryPermissionsInput);

        GetCurrentUserResponse response = new GetCurrentUserResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setPermissions(
                queryPermissionsOutput.getPermissions()
                        .stream()
                        .map(Permission::getPermissionName)
                        .collect(Collectors.toUnmodifiableSet()));
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
