package com.kiwiko.webapp.featureflags;

import com.kiwiko.webapp.featureflags.api.interfaces.FeatureFlagResolver;
import com.kiwiko.webapp.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.webapp.featureflags.internal.FeatureFlagCacheHelper;
import com.kiwiko.webapp.featureflags.internal.FeatureFlagEntityMapper;
import com.kiwiko.webapp.featureflags.internal.FeatureFlagServiceResolver;
import com.kiwiko.webapp.featureflags.internal.HybridFeatureFlagService;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntityDAO;
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
