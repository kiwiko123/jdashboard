package com.kiwiko.jdashboard.servicerequestkeys.client;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class ProvisionServiceRequestKeyOutput {
    private final @NonNull String requestKeyHeaderName;
    private final @NonNull String requestKey;
    
    // ISO-8061 date of when this request key expires
    private final @NonNull String expirationTime;
}
