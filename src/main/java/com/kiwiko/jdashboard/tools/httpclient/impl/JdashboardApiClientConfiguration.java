package com.kiwiko.jdashboard.tools.httpclient.impl;

import com.kiwiko.jdashboard.framework.caching.CachingConfiguration;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.HttpAuthenticationConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.environments.EnvironmentConfiguration;
import com.kiwiko.jdashboard.tools.httpclient.api.interfaces.JdashboardApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdashboardApiClientConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public JdashboardApiClient jdashboardApiClient() {
        return new JdashboardHttpApiClient();
    }

    @Bean
    public CoreHttpClient apiClientHttpClient() {
        return new CoreHttpClient();
    }

    @Bean
    @ConfiguredBy({EnvironmentConfiguration.class, HttpAuthenticationConfiguration.class})
    public ApiClientRequestHelper apiClientRequestHelper() {
        return new ApiClientRequestHelper();
    }

    @Bean
    public ApiClientResponseHelper apiClientResponseHelper() {
        return new ApiClientResponseHelper();
    }

    @Bean
    @ConfiguredBy({CachingConfiguration.class, LoggingConfiguration.class})
    public ApiClientCache apiClientCache() {
        return new ApiClientCache();
    }
}
