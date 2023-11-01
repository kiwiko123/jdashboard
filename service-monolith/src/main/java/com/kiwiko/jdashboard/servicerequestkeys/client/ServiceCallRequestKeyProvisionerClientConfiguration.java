package com.kiwiko.jdashboard.servicerequestkeys.client;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.servicerequestkeys.client.impl.JdashboardServiceCallRequestKeyProvisioner;
import com.kiwiko.jdashboard.servicerequestkeys.service.ServiceRequestKeyServiceExternalConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceCallRequestKeyProvisionerClientConfiguration {
    
    @Bean
    @ConfiguredBy({
            ServiceRequestKeyServiceExternalConfiguration.class,
            LoggingConfiguration.class,
    })
    public ServiceCallRequestKeyProvisioner serviceCallRequestKeyProvisioner() {
        return new JdashboardServiceCallRequestKeyProvisioner();
    }
}
