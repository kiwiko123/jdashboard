package com.kiwiko.jdashboard.webapp.application.events;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.jdashboard.webapp.application.events.internal.ApplicationEventEntityService;
import com.kiwiko.jdashboard.webapp.application.events.internal.data.ApplicationEventEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.application.events.internal.mappers.ApplicationEventEntityMapper;
import com.kiwiko.jdashboard.webapp.application.events.internal.streaming.ApplicationEventEmitter;
import com.kiwiko.jdashboard.webapp.application.events.internal.streaming.HttpApplicationEventEmitter;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ApplicationEventConfiguration.class)
public class ApplicationEventConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({TransactionConfiguration.class, PersistenceServicesCrudConfiguration.class})
    public ApplicationEventService applicationEventService() {
        return new ApplicationEventEntityService();
    }

    @Bean
    @ConfiguredBy({JdashboardApiClientConfiguration.class, LoggingConfiguration.class})
    public ApplicationEventEmitter applicationEventEmitter() {
        return new HttpApplicationEventEmitter();
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
