package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nonnull;

@Builder
@Getter
class RequestReferenceKeyMetadata {
    private long applicationEventId;
    private @Nonnull String serviceClientId;
}
