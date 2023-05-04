package com.kiwiko.jdashboard.featureflags.service;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.webapp.application.events.queue.ApplicationEventQueueConfiguration;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagEventClient;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagResolver;
import com.kiwiko.jdashboard.featureflags.service.api.interfaces.FeatureFlagService;
import com.kiwiko.jdashboard.featureflags.service.internal.events.ApplicationEventFeatureFlagEventClient;
import com.kiwiko.jdashboard.featureflags.service.internal.FeatureFlagEntityService;
import com.kiwiko.jdashboard.featureflags.service.internal.FeatureFlagServiceResolver;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonJsonConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = FeatureFlagConfiguration.class)
@ConfigurationScope(ConfigurationScopeLevel.PUBLIC)
public class FeatureFlagConfiguration {

    @Bean
    @ConfiguredBy({
            FeatureFlagInternalConfiguration.class,
            PersistenceServicesCrudConfiguration.class
    })
    public FeatureFlagService featureFlagService() {
        return new FeatureFlagEntityService();
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
