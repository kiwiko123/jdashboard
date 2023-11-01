package com.kiwiko.jdashboard.tools.apiclient.configuration;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.ApiClientRequestHelper;
import com.kiwiko.jdashboard.library.http.client.impl.CoreHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.HttpApiClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.HttpApiClientPlugins;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.JdashboardHttpApiClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.ApiClientResponseHelper;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.caching.HttpApiClientCachingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.HttpAuthenticationConfiguration;
import com.kiwiko.jdashboard.webapp.framework.security.environments.EnvironmentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.inject.Inject;

@Configuration
@Import(HttpApiClientDefaultPluginsConfiguration.class)
public class JdashboardApiClientConfiguration {

    @Inject private HttpApiClientDefaultPluginsConfiguration httpApiClientDefaultPluginsConfiguration;

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public JdashboardApiClient jdashboardApiClient() {
        return new JdashboardHttpApiClient();
    }

    @Bean
    @ConfiguredBy(HttpApiClientCachingConfiguration.class)
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

    @Deprecated
    @Bean
    @ConfiguredBy(HttpApiClientDefaultPluginsConfiguration.class)
    public HttpApiClientPlugins httpApiClientPlugins() {
        return new HttpApiClientPlugins(
                httpApiClientDefaultPluginsConfiguration.loggingPreRequestPlugin(),
                httpApiClientDefaultPluginsConfiguration.responseCachingPostRequestPlugin());
    }
}
