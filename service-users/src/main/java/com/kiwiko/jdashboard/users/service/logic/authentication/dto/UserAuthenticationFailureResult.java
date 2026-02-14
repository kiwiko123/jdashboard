package com.kiwiko.jdashboard.users.service.logic.authentication.dto;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Value
@EqualsAndHashCode(callSuper = true)
public class UserAuthenticationFailureResult extends UserAuthenticationResult {
    @NonNull String reason;
}
