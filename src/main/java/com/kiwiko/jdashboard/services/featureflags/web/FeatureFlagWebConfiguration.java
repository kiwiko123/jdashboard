package com.kiwiko.jdashboard.services.featureflags.web;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScope;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.services.featureflags.FeatureFlagConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PRIVATE)
public class FeatureFlagWebConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(FeatureFlagConfiguration.class)
    public FeatureFlagAPIHelper featureFlagAPIHelper() {
        return new FeatureFlagAPIHelper();
    }
}
