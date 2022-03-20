package com.kiwiko.jdashboard.services.featureflags.internal;

import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.services.featureflags.api.interfaces.FeatureFlagResolver;
import com.kiwiko.jdashboard.services.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlag;
import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlagStatus;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Optional;

public class FeatureFlagServiceResolver implements FeatureFlagResolver {

    @Inject private FeatureFlagService featureFlagService;

    @Override
    public boolean resolve(String name) {
        return resolve(name, null);
    }

    @Override
    public boolean resolve(String name, @Nullable Long userId) {
        Optional<FeatureFlag> flag = Optional.empty();

        // If a user is specified, first try searching for an individual flag tied to them.
        if (userId != null) {
            flag = featureFlagService.getForUser(name, userId);
        }

        if (flag.isEmpty()) {
            // Either a user was not provided, or no individual flag was found for them.
            // Search for a public flag.
            flag = featureFlagService.getByName(name)
                    .filter(this::isPublic);
        }

        return flag.map(FeatureFlag::getStatus)
                .map(FeatureFlagStatus.ENABLED.getId()::equalsIgnoreCase)
                .orElse(false);
    }

    private boolean isPublic(FeatureFlag flag) {
        return FeatureFlagUserScope.PUBLIC.getId().equalsIgnoreCase(flag.getUserScope());
    }
}
