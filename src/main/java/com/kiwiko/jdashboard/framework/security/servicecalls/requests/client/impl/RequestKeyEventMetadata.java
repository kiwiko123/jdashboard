package com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
class RequestKeyEventMetadata {
    private final @NonNull String scope;
    private final @NonNull String description;
}
