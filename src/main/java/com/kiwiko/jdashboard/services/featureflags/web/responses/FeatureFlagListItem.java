package com.kiwiko.jdashboard.services.featureflags.web.responses;

import com.kiwiko.jdashboard.clients.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.services.users.api.dto.User;

public class FeatureFlagListItem {
    private FeatureFlag featureFlag;
    private User user;

    public FeatureFlag getFeatureFlag() {
        return featureFlag;
    }

    public void setFeatureFlag(FeatureFlag featureFlag) {
        this.featureFlag = featureFlag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
