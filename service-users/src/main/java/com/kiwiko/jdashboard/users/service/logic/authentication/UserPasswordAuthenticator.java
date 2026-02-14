package com.kiwiko.jdashboard.users.service.logic.authentication;

import com.kiwiko.jdashboard.users.service.dto.User;
import com.kiwiko.jdashboard.users.service.dto.UserCredential;
import com.kiwiko.jdashboard.users.service.dto.UserCredentialType;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.AuthenticateUserPasswordInput;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.AuthenticateUserRequest;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.UserAuthenticationFailureResult;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.UserAuthenticationResult;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.UserAuthenticationStatus;
import com.kiwiko.jdashboard.users.service.logic.authentication.dto.UserAuthenticationSuccessResult;
import com.kiwiko.jdashboard.users.service.logic.crud.UserCredentialReader;
import com.kiwiko.jdashboard.users.service.logic.crud.UserReader;
import com.kiwiko.jdashboard.users.service.logic.encryption.UserCredentialEncryptor;
import jakarta.inject.Inject;

public class UserPasswordAuthenticator {
    @Inject private UserReader userReader;
    @Inject private UserCredentialEncryptor userCredentialEncryptor;
    @Inject private UserCredentialReader userCredentialReader;

    public UserAuthenticationResult authenticateUser(AuthenticateUserRequest request) {
        User user = userReader.getByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            return UserAuthenticationFailureResult.builder()
                    .status(UserAuthenticationStatus.FAILURE)
                    .reason("No user found")
                    .build();
        }

        AuthenticateUserPasswordInput authenticateUserPasswordInput = AuthenticateUserPasswordInput.builder()
                .userId(user.getId())
                .password(request.getPassword())
                .build();

        return authenticateUserPassword(authenticateUserPasswordInput);
    }

    private UserAuthenticationResult authenticateUserPassword(AuthenticateUserPasswordInput input) {
        UserCredential passwordCredential = userCredentialReader.getForUser(input.getUserId()).stream()
                .filter(credential -> UserCredentialType.PASSWORD.getId().equals(credential.getCredentialType()))
                .findFirst()
                .orElse(null);

        if (passwordCredential == null) {
            return UserAuthenticationFailureResult.builder()
                    .userId(input.getUserId())
                    .status(UserAuthenticationStatus.FAILURE)
                    .reason("No password found")
                    .build();
        }

        boolean matches = userCredentialEncryptor.matches(input.getPassword(), passwordCredential.getCredentialValue());
        if (matches) {
            return UserAuthenticationSuccessResult.builder()
                    .userId(input.getUserId())
                    .status(UserAuthenticationStatus.SUCCESS)
                    .build();
        }

        return UserAuthenticationFailureResult.builder()
                .userId(input.getUserId())
                .status(UserAuthenticationStatus.FAILURE)
                .reason("Unknown error")
                .build();
    }
}
