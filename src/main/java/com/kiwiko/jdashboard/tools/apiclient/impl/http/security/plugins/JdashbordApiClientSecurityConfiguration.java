package com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdashbordApiClientSecurityConfiguration {

    @Bean
    public ServiceRequestKeyProvisioningPreRequestPlugin serviceRequestKeyProvisioningPreRequestPlugin() {
        return new ServiceRequestKeyProvisioningPreRequestPlugin();
    }
}
