package com.kiwiko.jdashboard.featureflags.client.api.dto;

import lombok.Data;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
public class ResolvedFeatureFlag {
    public static ResolvedFeatureFlag unsuccessful() {
        return new ResolvedFeatureFlag();
    }

    private Long featureFlagId;
    private Long featureFlagContextId;
    private String flagName;
    private String scope;
    private String status;
    private @Nullable String flagValue;
    private Instant startDate;

    public boolean isEnabled() {
        return FeatureFlagStatus.ENABLED.getId().equals(status);
    }
}
