package com.kiwiko.jdashboard.servicerequestkeys.service.web;

import lombok.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public class ProvisionServiceRequestKeyRequest {
    private @Nonnull String serviceClientIdentifier;
    private @Nonnull String description;
    private @Nullable String expirationTime;
}
