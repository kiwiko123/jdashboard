package com.kiwiko.jdashboard.framework.caching;

import com.kiwiko.jdashboard.library.caching.api.ObjectCache;
import com.kiwiko.jdashboard.library.caching.impl.InMemoryObjectCache;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public ObjectCache objectCache() {
        return new InMemoryObjectCache();
    }
}
