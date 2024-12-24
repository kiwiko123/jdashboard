package com.kiwiko.jdashboard.tools.apiclient.configuration;

import com.kiwiko.jdashboard.tools.apiclient.impl.http.caching.HttpApiClientCachingConfiguration;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.LoggingPreRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.ResponseCachingPostRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2.DefaultJdashboardApiClientPlugins;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2.JsonContentTypeHeaderPreRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2.RequestLoggerPreRequestPlugin;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpApiClientDefaultPluginsConfiguration {

    @Bean
    public DefaultJdashboardApiClientPlugins defaultJdashboardApiClientPlugins() {
        return new DefaultJdashboardApiClientPlugins();
    }

    @Bean
    public RequestLoggerPreRequestPlugin requestLoggerPreRequestPlugin() {
        return new RequestLoggerPreRequestPlugin();
    }

    @Bean
    public JsonContentTypeHeaderPreRequestPlugin jsonContentTypeHeaderPreRequestPlugin() {
        return new JsonContentTypeHeaderPreRequestPlugin();
    }

    @Bean
    public LoggingPreRequestPlugin loggingPreRequestPlugin() {
        return new LoggingPreRequestPlugin();
    }

    @Bean
    @ConfiguredBy(HttpApiClientCachingConfiguration.class)
    public ResponseCachingPostRequestPlugin responseCachingPostRequestPlugin() {
        return new ResponseCachingPostRequestPlugin();
    }
}
