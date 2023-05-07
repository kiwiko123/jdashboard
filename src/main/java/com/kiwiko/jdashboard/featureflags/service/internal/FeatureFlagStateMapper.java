package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;

public class FeatureFlagStateMapper {
    public FeatureFlagState mapToFeatureFlagState(FeatureFlag featureFlag) {
        FeatureFlagState state = new FeatureFlagState();
        state.setFeatureFlagId(featureFlag.getId());
        state.setName(featureFlag.getName());
        state.setStatus(featureFlag.getStatus());
        state.setValue(featureFlag.getValue());
        state.setUserScope(featureFlag.getUserScope());

        return state;
    }
}
