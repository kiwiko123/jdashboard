package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.web;

import lombok.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public class ProvisionServiceRequestKeyRequest {
    private @Nonnull String serviceClientIdentifier;
    private @Nonnull String description;
    private @Nullable String expirationTime;
}
