package com.kiwiko.jdashboard.users.service.logic.authentication.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class AuthenticateUserPasswordInput {
    long userId;
    @NonNull String password;
}
