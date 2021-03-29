package com.kiwiko.webapp.flags.features.web.responses;

import com.kiwiko.webapp.flags.features.dto.FeatureFlag;
import com.kiwiko.webapp.users.data.User;

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
