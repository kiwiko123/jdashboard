package com.kiwiko.jdashboard.users.service.app.controller;

import com.kiwiko.jdashboard.users.service.logic.authentication.UserPasswordAuthenticator;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.AuthenticateUserRequest;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.UserAuthenticationResult;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.UserAuthenticationStatus;
import jakarta.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public-api/user-auth")
public class UserAuthController {
    @Inject private UserPasswordAuthenticator userPasswordAuthenticator;

    @PostMapping("/log-in")
    public ResponseEntity<UserAuthenticationResult> logUserIn(
            @RequestBody AuthenticateUserRequest request) {
        UserAuthenticationResult result = userPasswordAuthenticator.authenticateUser(request);

        if (result.getStatus() == UserAuthenticationStatus.FAILURE) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
