package com.kiwiko.jdashboard.clients.featureflags.api.dto;

import com.kiwiko.jdashboard.library.persistence.identification.Identifiable;

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
