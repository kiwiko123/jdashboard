package com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public class UpdateFeatureFlagInput {
    private @Nonnull String featureFlagName;
    private @Nonnull String userScope;
    private @Nullable Long userId;
}
