package com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service;

import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.interfaces.IncomingApplicationRequestLogService;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.IncomingApplicationRequestLogServiceImpl;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IncomingApplicationRequestLogServiceConfiguration {
    @Bean
    @ConfiguredBy({
            IncomingApplicationRequestLogServiceInternalConfiguration.class,
            PersistenceServicesCrudConfiguration.class
    })
    public IncomingApplicationRequestLogService incomingApplicationRequestLogService() {
        return new IncomingApplicationRequestLogServiceImpl();
    }
}
