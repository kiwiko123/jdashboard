package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
class RequestKeyEventMetadata {
    private @NonNull String scope;
    private @NonNull String serviceClientId;
    private @NonNull String description;
    private @NonNull Instant expirationTime;
    private String url;
}
