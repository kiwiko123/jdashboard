package com.kiwiko.jdashboard.users.service.logic.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class CreateUserInput {
    @NonNull String username;
    @NonNull String password;
}
