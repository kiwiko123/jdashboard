package com.kiwiko.jdashboard.webapp.featureflags;

import com.kiwiko.jdashboard.webapp.application.events.queue.ApplicationEventQueueConfiguration;
import com.kiwiko.jdashboard.webapp.featureflags.api.interfaces.FeatureFlagEventClient;
import com.kiwiko.jdashboard.webapp.featureflags.api.interfaces.FeatureFlagResolver;
import com.kiwiko.jdashboard.webapp.featureflags.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.webapp.featureflags.internal.events.ApplicationEventFeatureFlagEventClient;
import com.kiwiko.jdashboard.webapp.featureflags.internal.FeatureFlagEntityMapper;
import com.kiwiko.jdashboard.webapp.featureflags.internal.FeatureFlagEntityService;
import com.kiwiko.jdashboard.webapp.featureflags.internal.FeatureFlagServiceResolver;
import com.kiwiko.jdashboard.webapp.featureflags.internal.data.FeatureFlagEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = FeatureFlagConfiguration.class)
public class FeatureFlagConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(PersistenceServicesCrudConfiguration.class)
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
    @ConfiguredBy({ApplicationEventQueueConfiguration.class, GsonJsonConfiguration.class})
    public FeatureFlagEventClient featureFlagEventClient() {
        return new ApplicationEventFeatureFlagEventClient();
    }
}
