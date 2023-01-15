package com.kiwiko.jdashboard.framework.security.servicecalls.requests.client;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.impl.JdashboardServiceCallRequestKeyProvisioner;
import com.kiwiko.jdashboard.webapp.application.events.ApplicationEventConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.UniversalUniqueIdentifierConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceCallRequestKeyProvisionerClientConfiguration implements JdashboardDependencyConfiguration {
    
    @Bean
    @ConfiguredBy({
            ApplicationEventConfiguration.class,
            LoggingConfiguration.class,
            UniversalUniqueIdentifierConfiguration.class,
    })
    public ServiceCallRequestKeyProvisioner serviceCallRequestKeyProvisioner() {
        return new JdashboardServiceCallRequestKeyProvisioner();
    }
}
