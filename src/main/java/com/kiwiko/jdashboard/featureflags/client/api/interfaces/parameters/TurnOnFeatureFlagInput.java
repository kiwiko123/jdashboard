package com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurnOnFeatureFlagInput extends UpdateFeatureFlagInput {

    public TurnOnFeatureFlagInput(@Nonnull String featureFlagName, @Nullable Long userId) {
        super(featureFlagName, userId);
    }

    public TurnOnFeatureFlagInput() {
    }
}
