package com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
class UpdateFeatureFlagInput {
    private @Nonnull String featureFlagName;
    private @Nullable Long userId;
}
