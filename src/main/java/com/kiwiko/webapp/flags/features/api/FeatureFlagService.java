package com.kiwiko.webapp.flags.features.api;

import com.kiwiko.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.webapp.flags.features.dto.FeatureFlag;

import javax.annotation.Nullable;
import java.util.Optional;

public interface FeatureFlagService extends CreateReadUpdateDeleteAPI<FeatureFlag> {

    Optional<FeatureFlag> getByName(String name);

    Optional<FeatureFlag> getForUser(String name, long userId);
}
