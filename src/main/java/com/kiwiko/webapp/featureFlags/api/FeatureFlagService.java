package com.kiwiko.webapp.featureFlags.api;

import com.kiwiko.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.webapp.featureFlags.dto.FeatureFlag;

import java.util.Optional;
import java.util.Set;

public interface FeatureFlagService extends CreateReadUpdateDeleteAPI<FeatureFlag> {

    Optional<FeatureFlag> getByName(String name);

    Optional<FeatureFlag> getForUser(String name, long userId);

    Set<FeatureFlag> getAll();
}