package com.kiwiko.jdashboard.tools.apiclient.impl.http.caching;

import com.kiwiko.jdashboard.framework.caching.CachingConfiguration;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpApiClientCachingConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({CachingConfiguration.class, LoggingConfiguration.class})
    public ApiClientCache apiClientCache() {
        return new ApiClientCache();
    }
}
