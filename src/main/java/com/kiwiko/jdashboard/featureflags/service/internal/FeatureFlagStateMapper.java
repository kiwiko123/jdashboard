package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagContext;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedPublicFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedUserFeatureFlag;

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

    public ResolvedPublicFeatureFlag mapPublicFlag(FeatureFlag featureFlag, FeatureFlagContext featureFlagContext) {
        ResolvedPublicFeatureFlag resolvedFlag = new ResolvedPublicFeatureFlag();

        resolvedFlag.setFeatureFlagId(featureFlag.getId());
        resolvedFlag.setFeatureFlagContextId(featureFlagContext.getId());
        resolvedFlag.setFlagName(featureFlag.getName());
        resolvedFlag.setScope(featureFlagContext.getScope());
        resolvedFlag.setStatus(featureFlagContext.getFlagStatus());
        resolvedFlag.setFlagValue(featureFlagContext.getFlagValue());
        resolvedFlag.setStartDate(featureFlagContext.getStartDate());

        return resolvedFlag;
    }

    public ResolvedUserFeatureFlag mapUserFlag(FeatureFlag featureFlag, FeatureFlagContext featureFlagContext) {
        ResolvedUserFeatureFlag resolvedFlag = new ResolvedUserFeatureFlag();

        resolvedFlag.setFeatureFlagId(featureFlag.getId());
        resolvedFlag.setFeatureFlagContextId(featureFlagContext.getId());
        resolvedFlag.setFlagName(featureFlag.getName());
        resolvedFlag.setScope(featureFlagContext.getScope());
        resolvedFlag.setStatus(featureFlagContext.getFlagStatus());
        resolvedFlag.setUserId(featureFlagContext.getUserId());
        resolvedFlag.setFlagValue(featureFlagContext.getFlagValue());
        resolvedFlag.setStartDate(featureFlagContext.getStartDate());

        return resolvedFlag;
    }
}
