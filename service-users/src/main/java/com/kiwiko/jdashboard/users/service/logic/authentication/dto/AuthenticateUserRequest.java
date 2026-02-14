package com.kiwiko.jdashboard.users.service.logic.authentication.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class AuthenticateUserRequest {
    @NonNull String username;
    @NonNull String password;
}
