package com.kiwiko.jdashboard.servicerequestkeys.client.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;

@Builder
@Getter
class RequestKeyEventMetadata {
    private final @NonNull String scope;
    private final @NonNull String serviceClientId;
    private final @NonNull String description;
    private final @NonNull Instant expirationTime;
}
