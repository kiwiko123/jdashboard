package com.kiwiko.jdashboard.services.featureflags.api.interfaces;

import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlag;

public interface FeatureFlagEventClient {

    void createCreateFeatureFlagEvent(FeatureFlag featureFlag);
    void createUpdateFeatureFlagEvent(FeatureFlag featureFlag);
    void createMergeFeatureFlagEvent(FeatureFlag featureFlag);
    void createDeleteFeatureFlagEvent(long featureFlagId);
}
