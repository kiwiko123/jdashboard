package com.kiwiko.jdashboard.tools.apiclient;

import com.kiwiko.jdashboard.framework.caching.CachingConfiguration;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.ApiClientCache;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.ApiClientRequestHelper;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.CoreHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.HttpApiClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.HttpApiClientPlugins;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.JdashboardHttpApiClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.ApiClientResponseHelper;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.HttpApiClientDefaultPluginsConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.HttpAuthenticationConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.environments.EnvironmentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.inject.Inject;

@Configuration
@Import(HttpApiClientDefaultPluginsConfiguration.class)
public class JdashboardApiClientConfiguration implements JdashboardDependencyConfiguration {

    @Inject private HttpApiClientDefaultPluginsConfiguration httpApiClientDefaultPluginsConfiguration;

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public JdashboardApiClient jdashboardApiClient() {
        return new JdashboardHttpApiClient();
    }

    @Bean
    public HttpApiClient httpApiClient() {
        return new HttpApiClient();
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

    @Bean
    @ConfiguredBy(HttpApiClientDefaultPluginsConfiguration.class)
    public HttpApiClientPlugins httpApiClientPlugins() {
        return new HttpApiClientPlugins(httpApiClientDefaultPluginsConfiguration.loggingPreRequestPlugin());
    }
}
