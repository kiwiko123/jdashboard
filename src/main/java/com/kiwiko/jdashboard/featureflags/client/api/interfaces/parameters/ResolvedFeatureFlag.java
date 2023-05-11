package com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;

import javax.annotation.Nullable;
import java.util.Optional;

public class ResolvedFeatureFlag {
    private final @Nullable FeatureFlagState featureFlagState;

    public ResolvedFeatureFlag(@Nullable FeatureFlagState featureFlagState) {
        this.featureFlagState = featureFlagState;
    }

    public Optional<FeatureFlagState> get() {
        return Optional.ofNullable(featureFlagState);
    }
}
