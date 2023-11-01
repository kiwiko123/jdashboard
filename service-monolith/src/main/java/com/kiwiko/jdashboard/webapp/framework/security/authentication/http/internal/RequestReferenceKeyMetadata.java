package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@Getter
class RequestReferenceKeyMetadata {
    private long applicationEventId;
    private @NonNull String serviceClientId;
    private @Nullable String scope;
    private @NonNull Instant expirationTime;
}
