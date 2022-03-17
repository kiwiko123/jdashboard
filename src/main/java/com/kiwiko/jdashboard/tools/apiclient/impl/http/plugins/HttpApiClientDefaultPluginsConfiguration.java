package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpApiClientDefaultPluginsConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public LoggingPreRequestPlugin loggingPreRequestPlugin() {
        return new LoggingPreRequestPlugin();
    }
}
