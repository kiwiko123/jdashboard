package com.kiwiko.jdashboard.webapp.http.client;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.HttpAuthenticationConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.environments.EnvironmentConfiguration;
import com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.ApiClientHttpClient;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.ApiClientRequestHelper;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.ApiClientResponseHelper;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.JdashboardHttpApiClient;
import com.kiwiko.library.http.client.api.AsynchronousHttpRequestClient;
import com.kiwiko.library.http.client.api.SynchronousHttpRequestClient;
import com.kiwiko.library.http.client.internal.HttpNetAsynchronousRequestClient;
import com.kiwiko.library.http.client.internal.HttpNetSynchronousRequestClient;
import com.kiwiko.jdashboard.webapp.http.client.impl.JdashboardHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdashboardHttpClientConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public JdashboardApiClient jdashboardApiClient() {
        return new JdashboardHttpApiClient();
    }

    @Bean
    public ApiClientHttpClient apiClientHttpClient() {
        return new ApiClientHttpClient();
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
    public JdashboardHttpClient jdashboardHttpClient() {
        return new JdashboardHttpClient();
    }

    @Bean
    public SynchronousHttpRequestClient synchronousHttpRequestClient() {
        return new HttpNetSynchronousRequestClient();
    }

    @Bean
    public AsynchronousHttpRequestClient asynchronousHttpRequestClient() {
        return new HttpNetAsynchronousRequestClient();
    }
}
