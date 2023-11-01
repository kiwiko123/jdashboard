package com.kiwiko.jdashboard.featureflags.service.web.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModifyFeatureFlagData {
    private long featureFlagId;
    private String featureFlagName;
    private List<FeatureFlagListItemRule> rules;
}
