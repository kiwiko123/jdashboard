package com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters;

import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetFeatureFlagOutput {
    private final FeatureFlag featureFlag;
}
