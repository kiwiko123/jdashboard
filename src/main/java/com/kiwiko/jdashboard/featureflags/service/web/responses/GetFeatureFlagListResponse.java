package com.kiwiko.jdashboard.featureflags.service.web.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetFeatureFlagListResponse {
    private List<FeatureFlagListItemV2> listItems;
    private long currentUserId;
}
