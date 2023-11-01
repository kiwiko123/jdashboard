package com.kiwiko.jdashboard.featureflags.client.api.interfaces;

import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;

import java.util.Optional;

public interface FeatureFlagClient {

    GetFeatureFlagOutput getFlag(GetFeatureFlagInput input);

    Optional<Boolean> isOn(String featureFlagName);

    Optional<ResolvedFeatureFlag> resolveForPublic(String featureFlagName);
    Optional<ResolvedFeatureFlag> resolveForUser(String featureFlagName, Long userId);
    Optional<ResolvedFeatureFlag> resolve(String featureFlagName);

    ClientResponse<ResolvedFeatureFlag> turnOnForPublic(String featureFlagName);
    ClientResponse<ResolvedFeatureFlag> turnOnForUser(String featureFlagName, Long userId);
    ClientResponse<ResolvedFeatureFlag> turnOn(String featureFlagName);

    ClientResponse<ResolvedFeatureFlag> turnOffForPublic(String featureFlagName);
    ClientResponse<ResolvedFeatureFlag> turnOffForUser(String featureFlagName, Long userId);
    ClientResponse<ResolvedFeatureFlag> turnOff(String featureFlagName);
}
