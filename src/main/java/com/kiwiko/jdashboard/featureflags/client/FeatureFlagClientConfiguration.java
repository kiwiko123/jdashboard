package com.kiwiko.jdashboard.featureflags.client;

import com.kiwiko.jdashboard.featureflags.client.api.interfaces.FeatureFlagClient;
import com.kiwiko.jdashboard.featureflags.client.impl.http.FeatureFlagHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.configuration.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.requests.RequestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeatureFlagClientConfiguration {

    @Bean
    @ConfiguredBy({
            JdashboardApiClientConfiguration.class,
            RequestConfiguration.class,
    })
    public FeatureFlagClient featureFlagClient() {
        return new FeatureFlagHttpClient();
    }
}
