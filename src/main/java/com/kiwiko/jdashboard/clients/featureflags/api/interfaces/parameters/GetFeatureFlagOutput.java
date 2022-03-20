package com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters;

import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlag;

public class GetFeatureFlagOutput {
    private FeatureFlag featureFlag;

    public FeatureFlag getFeatureFlag() {
        return featureFlag;
    }

    public void setFeatureFlag(FeatureFlag featureFlag) {
        this.featureFlag = featureFlag;
    }
}
