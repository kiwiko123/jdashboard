package com.kiwiko.jdashboard.featureflags.client.api.dto;

import com.kiwiko.jdashboard.library.persistence.identification.Identifiable;

import java.util.Map;

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

    public static String opposite(String status) {
        return STATUS_OPPOSITES.getOrDefault(status, DISABLED.getId());
    }

    private static final Map<String, String> STATUS_OPPOSITES = Map.of(
            ENABLED.getId(), DISABLED.getId(),
            DISABLED.getId(), ENABLED.getId());
}
