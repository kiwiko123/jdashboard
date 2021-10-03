package com.kiwiko.webapp.featureflags.api.interfaces;

import com.kiwiko.webapp.featureflags.api.dto.FeatureFlag;

public interface FeatureFlagEventClient {

    void createCreateFeatureFlagEvent(FeatureFlag featureFlag);
    void createUpdateFeatureFlagEvent(FeatureFlag featureFlag);
    void createMergeFeatureFlagEvent(FeatureFlag featureFlag);
    void createDeleteFeatureFlagEvent(long featureFlagId);
}
