package com.kiwiko.jdashboard.webapp.application.events;

import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.jdashboard.webapp.application.events.internal.ApplicationEventEntityService;
import com.kiwiko.jdashboard.webapp.application.events.internal.data.ApplicationEventEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.application.events.internal.mappers.ApplicationEventEntityMapper;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ApplicationEventConfiguration.class)
public class ApplicationEventConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public ApplicationEventService applicationEventService() {
        return new ApplicationEventEntityService();
    }

    @Bean
    public ApplicationEventEntityDataFetcher applicationEventEntityDataFetcher() {
        return new ApplicationEventEntityDataFetcher();
    }

    @Bean
    public ApplicationEventEntityMapper applicationEventEntityMapper() {
        return new ApplicationEventEntityMapper();
    }
}
