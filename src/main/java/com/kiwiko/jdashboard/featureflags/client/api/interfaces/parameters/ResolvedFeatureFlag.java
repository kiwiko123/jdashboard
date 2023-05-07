package com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResolvedFeatureFlag {
    private FeatureFlagState state;
}
