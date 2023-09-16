package com.kiwiko.jdashboard.featureflags.service.api.interfaces;

import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedPublicFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedUserFeatureFlag;

import java.util.Optional;

public interface FeatureFlagStateService {
    Optional<ResolvedPublicFeatureFlag> getPublicFlagByName(String name);

    Optional<ResolvedUserFeatureFlag> getUserFlagByName(String name, Long userId);

    ResolvedPublicFeatureFlag toggle(String name);

    ResolvedUserFeatureFlag toggle(String name, Long userId);

    ResolvedPublicFeatureFlag turnOn(String name);

    ResolvedUserFeatureFlag turnOn(String name, Long userId);

    ResolvedPublicFeatureFlag turnOff(String name);

    ResolvedUserFeatureFlag turnOff(String name, Long userId);
}
