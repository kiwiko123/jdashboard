package com.kiwiko.jdashboard.services.featureflags.api.dto;

import com.kiwiko.jdashboard.library.persistence.identification.Identifiable;

public enum FeatureFlagUserScope implements Identifiable<String> {
    INDIVIDUAL("individual"), // Feature flag is enabled/disabled for a single User.
    PUBLIC("public");     // Feature flag is enabled for either everyone, or no one.

    private final String id;

    FeatureFlagUserScope(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
