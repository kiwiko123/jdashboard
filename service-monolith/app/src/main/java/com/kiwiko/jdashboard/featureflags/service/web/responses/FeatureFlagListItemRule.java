package com.kiwiko.jdashboard.featureflags.service.web.responses;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.time.Instant;

@Getter
@Setter
public class FeatureFlagListItemRule {
    private long featureFlagRuleId;
    private long featureFlagId;
    private String flagStatus;
    private String scope;
    private @Nullable Long userId;
    private @Nullable String flagValue;
    private Instant startDate;
    private @Nullable Instant endDate;
    private boolean isOn;
}
