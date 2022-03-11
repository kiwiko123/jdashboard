package com.kiwiko.jdashboard.services.userauth.web;

import com.kiwiko.jdashboard.services.userauth.internal.UserLoginAuthenticator;
import com.kiwiko.jdashboard.services.userauth.api.interfaces.exceptions.UserAuthenticationException;
import com.kiwiko.jdashboard.services.userauth.web.dto.LogUserInInput;
import com.kiwiko.jdashboard.services.userauth.web.dto.UserLoginData;
import com.kiwiko.jdashboard.services.userauth.web.dto.LogUserInOutput;
import com.kiwiko.jdashboard.framework.controllers.api.interfaces.JdashboardConfigured;
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

    @PostMapping("/users/log-in")
    public LogUserInOutput logUserIn(
            @RequestBody UserLoginData userLoginData,
            HttpServletResponse httpServletResponse) throws UserAuthenticationException {
        LogUserInInput logUserInInput = new LogUserInInput();
        logUserInInput.setUserLoginData(userLoginData);
        logUserInInput.setHttpServletResponse(httpServletResponse);;

        return userLoginAuthenticator.logUserIn(logUserInInput);
    }
}
