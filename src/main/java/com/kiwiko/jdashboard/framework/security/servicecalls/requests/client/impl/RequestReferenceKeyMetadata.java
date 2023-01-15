package com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.impl;

import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Instant;

@Builder
@Getter
class RequestReferenceKeyMetadata {
    private long applicationEventId;
    private @Nonnull String serviceClientId;
    private @Nullable String scope;
    private @Nonnull Instant expirationTime;
}
