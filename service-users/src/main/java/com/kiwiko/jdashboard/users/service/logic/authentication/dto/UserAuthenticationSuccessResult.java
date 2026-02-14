package com.kiwiko.jdashboard.users.service.logic.authentication.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Value
@EqualsAndHashCode(callSuper = true)
public class UserAuthenticationSuccessResult extends UserAuthenticationResult {
}
