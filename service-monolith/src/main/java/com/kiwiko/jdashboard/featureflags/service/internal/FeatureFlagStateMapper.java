package com.kiwiko.jdashboard.featureflags.service.internal;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagRule;
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

    public ResolvedPublicFeatureFlag mapPublicFlag(FeatureFlag featureFlag, FeatureFlagRule featureFlagRule) {
        ResolvedPublicFeatureFlag resolvedFlag = new ResolvedPublicFeatureFlag();

        resolvedFlag.setFeatureFlagId(featureFlag.getId());
        resolvedFlag.setFeatureFlagRuleId(featureFlagRule.getId());
        resolvedFlag.setFlagName(featureFlag.getName());
        resolvedFlag.setScope(featureFlagRule.getScope());
        resolvedFlag.setStatus(featureFlagRule.getFlagStatus());
        resolvedFlag.setFlagValue(featureFlagRule.getFlagValue());
        resolvedFlag.setStartDate(featureFlagRule.getStartDate());

        return resolvedFlag;
    }

    public ResolvedUserFeatureFlag mapUserFlag(FeatureFlag featureFlag, FeatureFlagRule featureFlagRule) {
        ResolvedUserFeatureFlag resolvedFlag = new ResolvedUserFeatureFlag();

        resolvedFlag.setFeatureFlagId(featureFlag.getId());
        resolvedFlag.setFeatureFlagRuleId(featureFlagRule.getId());
        resolvedFlag.setFlagName(featureFlag.getName());
        resolvedFlag.setScope(featureFlagRule.getScope());
        resolvedFlag.setStatus(featureFlagRule.getFlagStatus());
        resolvedFlag.setUserId(featureFlagRule.getUserId());
        resolvedFlag.setFlagValue(featureFlagRule.getFlagValue());
        resolvedFlag.setStartDate(featureFlagRule.getStartDate());

        return resolvedFlag;
    }
}
