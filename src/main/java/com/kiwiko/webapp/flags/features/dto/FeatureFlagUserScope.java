package com.kiwiko.webapp.flags.features.dto;

import com.kiwiko.library.persistence.identification.Identifiable;

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
