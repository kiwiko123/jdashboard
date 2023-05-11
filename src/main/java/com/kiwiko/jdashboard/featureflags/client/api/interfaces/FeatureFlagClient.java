package com.kiwiko.jdashboard.featureflags.client.api.interfaces;

import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.ResolvedFeatureFlag;

public interface FeatureFlagClient {

    GetFeatureFlagOutput getFlag(GetFeatureFlagInput input);

    ResolvedFeatureFlag resolveForPublic(String featureFlagName);
    ResolvedFeatureFlag resolveForUser(String featureFlagName, Long userId);
    ResolvedFeatureFlag resolve(String featureFlagName);

    ResolvedFeatureFlag turnOnForPublic(String featureFlagName);
    ResolvedFeatureFlag turnOnForUser(String featureFlagName, Long userId);
    ResolvedFeatureFlag turnOn(String featureFlagName);

    ResolvedFeatureFlag turnOffForPublic(String featureFlagName);
    ResolvedFeatureFlag turnOffForUser(String featureFlagName, Long userId);
    ResolvedFeatureFlag turnOff(String featureFlagName);
}
