package com.kiwiko.services.featureFlags;

import com.kiwiko.services.featureFlags.api.FeatureFlagResolver;
import com.kiwiko.services.featureFlags.api.FeatureFlagService;
import com.kiwiko.services.featureFlags.internal.FeatureFlagCacheHelper;
import com.kiwiko.services.featureFlags.internal.FeatureFlagEntityMapper;
import com.kiwiko.services.featureFlags.internal.FeatureFlagServiceResolver;
import com.kiwiko.services.featureFlags.internal.HybridFeatureFlagService;
import com.kiwiko.services.featureFlags.internal.database.FeatureFlagEntityDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagConfiguration {

    @Bean
    public FeatureFlagService featureFlagService() {
        return new HybridFeatureFlagService();
    }

    @Bean
    public FeatureFlagEntityDAO featureFlagEntityDAO() {
        return new FeatureFlagEntityDAO();
    }

    @Bean
    public FeatureFlagEntityMapper featureFlagEntityMapper() {
        return new FeatureFlagEntityMapper();
    }

    @Bean
    public FeatureFlagCacheHelper featureFlagCacheHelper() {
        return new FeatureFlagCacheHelper();
    }

    @Bean
    public FeatureFlagResolver featureFlagResolver() {
        return new FeatureFlagServiceResolver();
    }
}
