package com.kiwiko.webapp.flags.features;

import com.kiwiko.webapp.flags.features.api.FeatureFlagResolver;
import com.kiwiko.webapp.flags.features.api.FeatureFlagService;
import com.kiwiko.webapp.flags.features.internal.FeatureFlagCacheHelper;
import com.kiwiko.webapp.flags.features.internal.FeatureFlagEntityMapper;
import com.kiwiko.webapp.flags.features.internal.FeatureFlagServiceResolver;
import com.kiwiko.webapp.flags.features.internal.HybridFeatureFlagService;
import com.kiwiko.webapp.flags.features.internal.database.FeatureFlagEntityDAO;
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
