package com.kiwiko.jdashboard.services.featureflags.api.interfaces;

import com.kiwiko.jdashboard.services.featureflags.api.dto.FeatureFlagUserScope;

import javax.annotation.Nullable;

public interface FeatureFlagResolver {

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
    boolean resolve(String name, @Nullable Long userId);

    /**
     * Shortcut for {@code resolve(name, null)}.
     *
     * @see #resolve(String, Long)
     */
    boolean resolve(String name);
}
