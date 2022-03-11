package com.kiwiko.jdashboard.webapp.featureflags.web;

import com.kiwiko.jdashboard.webapp.featureflags.FeatureFlagConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagWebConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(FeatureFlagConfiguration.class)
    public FeatureFlagAPIHelper featureFlagAPIHelper() {
        return new FeatureFlagAPIHelper();
    }
}
