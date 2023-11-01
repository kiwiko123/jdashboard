package com.kiwiko.jdashboard.featureflags.service.web;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.featureflags.service.FeatureFlagConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PRIVATE)
public class FeatureFlagWebConfiguration {

    @Bean
    @ConfiguredBy(FeatureFlagConfiguration.class)
    public FeatureFlagAPIHelper featureFlagAPIHelper() {
        return new FeatureFlagAPIHelper();
    }
}
