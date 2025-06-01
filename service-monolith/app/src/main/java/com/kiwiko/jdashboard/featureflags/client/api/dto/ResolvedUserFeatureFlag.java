package com.kiwiko.jdashboard.featureflags.client.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResolvedUserFeatureFlag extends ResolvedFeatureFlag {
    private String scope = FeatureFlagUserScope.INDIVIDUAL.getId();
    private Long userId;
}
