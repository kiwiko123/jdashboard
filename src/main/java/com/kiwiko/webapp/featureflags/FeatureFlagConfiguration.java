package com.kiwiko.webapp.featureflags;

import com.kiwiko.webapp.featureflags.api.interfaces.FeatureFlagEventClient;
import com.kiwiko.webapp.featureflags.api.interfaces.FeatureFlagResolver;
import com.kiwiko.webapp.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.webapp.featureflags.internal.events.ApplicationEventFeatureFlagEventClient;
import com.kiwiko.webapp.featureflags.internal.FeatureFlagEntityMapper;
import com.kiwiko.webapp.featureflags.internal.FeatureFlagEntityService;
import com.kiwiko.webapp.featureflags.internal.FeatureFlagServiceResolver;
import com.kiwiko.webapp.featureflags.internal.data.FeatureFlagEntityDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = FeatureFlagConfiguration.class)
public class FeatureFlagConfiguration {

    @Bean
    public FeatureFlagService featureFlagService() {
        return new FeatureFlagEntityService();
    }

    @Bean
    public FeatureFlagEntityDataFetcher featureFlagEntityDAO() {
        return new FeatureFlagEntityDataFetcher();
    }

    @Bean
    public FeatureFlagEntityMapper featureFlagEntityMapper() {
        return new FeatureFlagEntityMapper();
    }

    @Bean
    public FeatureFlagResolver featureFlagResolver() {
        return new FeatureFlagServiceResolver();
    }

    @Bean
    public FeatureFlagEventClient featureFlagEventClient() {
        return new ApplicationEventFeatureFlagEventClient();
    }
}
