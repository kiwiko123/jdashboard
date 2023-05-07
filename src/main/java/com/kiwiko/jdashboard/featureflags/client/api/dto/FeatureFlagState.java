package com.kiwiko.jdashboard.featureflags.client.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@NoArgsConstructor
@Data
public class FeatureFlagState {
    private Long featureFlagId;
    private String name;
    private String status;
    private @Nullable String value;
    private String userScope;
}
