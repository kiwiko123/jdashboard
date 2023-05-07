package com.kiwiko.jdashboard.featureflags.service.api.interfaces;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagState;

import java.util.Optional;

public interface FeatureFlagStateService {
    Optional<FeatureFlagState> getByName(String name);

    Optional<FeatureFlagState> getForUser(String name, Long userId);

    FeatureFlagState toggle(String name);

    FeatureFlagState toggle(String name, Long userId);

    FeatureFlagState turnOn(String name);

    FeatureFlagState turnOn(String name, Long userId);

    FeatureFlagState turnOff(String name);

    FeatureFlagState turnOff(String name, Long userId);
}
