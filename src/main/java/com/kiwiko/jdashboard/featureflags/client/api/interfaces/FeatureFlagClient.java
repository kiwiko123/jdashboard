package com.kiwiko.jdashboard.featureflags.client.api.interfaces;

import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.GetFeatureFlagOutput;

public interface FeatureFlagClient {

    GetFeatureFlagOutput getFlag(GetFeatureFlagInput input);
}
