package com.kiwiko.webapp.flags.features.api;

import javax.annotation.Nullable;

public interface FeatureFlagResolver {

    boolean resolve(String name);
    boolean resolve(String name, @Nullable Long userId);
}
