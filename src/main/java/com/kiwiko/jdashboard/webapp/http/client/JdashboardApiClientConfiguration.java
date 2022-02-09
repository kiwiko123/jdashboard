package com.kiwiko.jdashboard.webapp.http.client;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.HttpAuthenticationConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.environments.EnvironmentConfiguration;
import com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.CoreHttpClient;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.ApiClientRequestHelper;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.ApiClientResponseHelper;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.JdashboardHttpApiClient;
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
}
