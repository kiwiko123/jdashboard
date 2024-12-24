package com.kiwiko.jdashboard.featureflags.client.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResolvedPublicFeatureFlag extends ResolvedFeatureFlag {
    private String scope = FeatureFlagUserScope.PUBLIC.getId();
}
