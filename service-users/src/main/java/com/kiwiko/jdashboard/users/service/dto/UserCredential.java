package com.kiwiko.jdashboard.users.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCredential {
    private Long id;
    private Long userId;
    private String credentialType;
    private String credentialValue;
    private Instant createdDate;
    private boolean isRemoved;
}
