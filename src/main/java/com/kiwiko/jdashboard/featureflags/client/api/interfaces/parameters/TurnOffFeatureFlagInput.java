package com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TurnOffFeatureFlagInput extends UpdateFeatureFlagInput {

    public TurnOffFeatureFlagInput(@Nonnull String featureFlagName, @Nullable Long userId) {
        super(featureFlagName, userId);
    }

    public TurnOffFeatureFlagInput() {
    }
}
