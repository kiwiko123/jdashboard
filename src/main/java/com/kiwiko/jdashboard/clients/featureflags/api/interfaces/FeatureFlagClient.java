package com.kiwiko.jdashboard.clients.featureflags.api.interfaces;

import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagInput;
import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagOutput;

public interface FeatureFlagClient {

    GetFeatureFlagOutput getFlag(GetFeatureFlagInput input);
}
