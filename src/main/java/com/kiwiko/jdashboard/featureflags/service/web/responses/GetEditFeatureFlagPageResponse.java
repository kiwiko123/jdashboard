package com.kiwiko.jdashboard.featureflags.service.web.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class GetEditFeatureFlagPageResponse {
    private long featureFlagId;
    private String featureFlagName;
    private Instant createdDate;
    private Instant lastUpdatedDate;
    private boolean isOnForMe;
    private boolean isOnForPublic;
    private List<FeatureFlagListItemRule> rules;
}
