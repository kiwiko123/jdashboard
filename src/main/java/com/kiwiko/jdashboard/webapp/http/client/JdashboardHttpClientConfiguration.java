package com.kiwiko.jdashboard.webapp.http.client;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.environments.EnvironmentConfiguration;
import com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.http.client.impl.apiclient.HttpClientHelper;
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
    @ConfiguredBy(EnvironmentConfiguration.class)
    public JdashboardApiClient jdashboardApiClient() {
        return new JdashboardHttpApiClient();
    }

    @Bean
    public HttpClientHelper httpClientHelper() {
        return new HttpClientHelper();
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
