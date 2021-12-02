package com.kiwiko.jdashboard.webapp.featureflags.web.responses;

import com.kiwiko.jdashboard.webapp.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.webapp.users.data.User;

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
