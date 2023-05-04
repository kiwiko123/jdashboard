package com.kiwiko.jdashboard.featureflags.service.api.interfaces;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlag;

public interface FeatureFlagEventClient {

    void createCreateFeatureFlagEvent(FeatureFlag featureFlag);
    void createUpdateFeatureFlagEvent(FeatureFlag featureFlag);
    void createMergeFeatureFlagEvent(FeatureFlag featureFlag);
    void createDeleteFeatureFlagEvent(long featureFlagId);
}
