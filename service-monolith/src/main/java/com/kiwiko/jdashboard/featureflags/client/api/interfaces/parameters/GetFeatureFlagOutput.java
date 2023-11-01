package com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetFeatureFlagOutput {
    private final FeatureFlag featureFlag;
}
