package com.kiwiko.jdashboard.clients.featureflags;

import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.clients.featureflags.impl.http.FeatureFlagHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.configuration.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagClientConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(JdashboardApiClientConfiguration.class)
    public FeatureFlagClient featureFlagClient() {
        return new FeatureFlagHttpClient();
    }
}
