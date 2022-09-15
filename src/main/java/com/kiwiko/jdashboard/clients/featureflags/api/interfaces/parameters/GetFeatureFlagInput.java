package com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@Builder
public class GetFeatureFlagInput {
    private @Nullable Long featureFlagId;
    private @Nullable String featureFlagName;
    private @Nullable Long userId;
}
