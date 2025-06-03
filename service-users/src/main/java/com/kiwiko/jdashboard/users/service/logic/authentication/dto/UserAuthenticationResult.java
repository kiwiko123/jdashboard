package com.kiwiko.jdashboard.users.service.logic.authentication.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode
public class UserAuthenticationResult {
    private long userId;
    private UserAuthenticationStatus status;
}
