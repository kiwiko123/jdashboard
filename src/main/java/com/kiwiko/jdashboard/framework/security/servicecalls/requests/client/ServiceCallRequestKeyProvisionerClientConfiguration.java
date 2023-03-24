package com.kiwiko.jdashboard.framework.security.servicecalls.requests.client;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.impl.JdashboardServiceCallRequestKeyProvisioner;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.ServiceRequestKeyServiceConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceCallRequestKeyProvisionerClientConfiguration implements JdashboardDependencyConfiguration {
    
    @Bean
    @ConfiguredBy({
            ServiceRequestKeyServiceConfiguration.class,
            LoggingConfiguration.class,
    })
    public ServiceCallRequestKeyProvisioner serviceCallRequestKeyProvisioner() {
        return new JdashboardServiceCallRequestKeyProvisioner();
    }
}
