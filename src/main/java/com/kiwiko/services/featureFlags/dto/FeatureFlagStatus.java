package com.kiwiko.services.featureFlags.dto;

import com.kiwiko.library.persistence.identification.Identifiable;

public enum FeatureFlagStatus implements Identifiable<String> {
    ENABLED("enabled"),
    DISABLED("disabled");

    private final String id;

    FeatureFlagStatus(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
