package com.kiwiko.jdashboard.servicerequestkeys.client;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@Builder
@Getter
public class ProvisionServiceRequestKeyInput {
    private final @NonNull String serviceClientIdentifier;
    private final @NonNull String description;
    private final @Nullable String expirationTime;
}
