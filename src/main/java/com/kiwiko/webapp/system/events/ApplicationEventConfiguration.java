package com.kiwiko.webapp.system.events;

import com.kiwiko.webapp.system.events.api.interfaces.ApplicationEventService;
import com.kiwiko.webapp.system.events.internal.ApplicationEventEntityService;
import com.kiwiko.webapp.system.events.internal.data.ApplicationEventEntityDataFetcher;
import com.kiwiko.webapp.system.events.internal.mappers.ApplicationEventEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ApplicationEventConfiguration.class)
public class ApplicationEventConfiguration {

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
