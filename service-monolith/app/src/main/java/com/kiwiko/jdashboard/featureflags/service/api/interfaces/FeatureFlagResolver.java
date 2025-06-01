package com.kiwiko.jdashboard.featureflags.service.api.interfaces;

import com.kiwiko.jdashboard.featureflags.client.api.dto.FeatureFlagUserScope;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedPublicFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedUserFeatureFlag;

import javax.annotation.Nullable;
import java.util.Optional;

public interface FeatureFlagResolver {

    Optional<? extends ResolvedFeatureFlag> resolve(String featureFlagName);

    Optional<? extends ResolvedFeatureFlag> resolve(String featureFlagName, @Nullable Long userId);

    Optional<ResolvedPublicFeatureFlag> resolveForPublic(String featureFlagName);

    Optional<ResolvedUserFeatureFlag> resolveForUser(String featureFlagName, long userId);

    /**
     * Search for a feature flag that matches the input parameters.
     * If a user ID is provided, search for a single matching
     * {@link FeatureFlagUserScope#INDIVIDUAL} feature flag.
     *
     * If no user ID is provided, search for a single matching
     * {@link FeatureFlagUserScope#PUBLIC} feature flag.
     *
     * @param name the feature flag's name
     * @param userId the user ID (optional)
     * @return true if the flag is enabled, or false if not
     */
    boolean resolveLegacy(String name, @Nullable Long userId);

    /**
     * Shortcut for {@code resolve(name, null)}.
     *
     * @see #resolveLegacy(String, Long)
     */
    boolean resolveLegacy(String name);
}
