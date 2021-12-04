package com.kiwiko.jdashboard.webapp.featureflags.api.interfaces;

import com.kiwiko.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.jdashboard.webapp.featureflags.api.dto.FeatureFlag;

import java.util.Optional;
import java.util.Set;

public interface FeatureFlagService extends CreateReadUpdateDeleteAPI<FeatureFlag> {

    Optional<FeatureFlag> getByName(String name);

    Optional<FeatureFlag> getForUser(String name, long userId);

    Set<FeatureFlag> getAll();

    FeatureFlag merge(FeatureFlag featureFlag);
}
