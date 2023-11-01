package com.kiwiko.jdashboard.servicerequestkeys.service;

import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.app.ServiceRequestKeyAppService;
import com.kiwiko.jdashboard.servicerequestkeys.service.internal.app.ServiceRequestKeyAppServiceImpl;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.servicerequestkeys.service.internal.ServiceRequestKeyEntityMapper;
import com.kiwiko.jdashboard.servicerequestkeys.service.internal.data.ServiceRequestKeyEntityDataAccessObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PACKAGE)
public class ServiceRequestKeyServiceInternalConfiguration {

    @Bean
    public ServiceRequestKeyEntityDataAccessObject serviceRequestKeyEntityDataAccessObject() {
        return new ServiceRequestKeyEntityDataAccessObject();
    }

    @Bean
    public ServiceRequestKeyEntityMapper serviceRequestKeyEntityMapper() {
        return new ServiceRequestKeyEntityMapper();
    }

    @Bean
    public ServiceRequestKeyAppService serviceRequestKeyAppService() {
        return new ServiceRequestKeyAppServiceImpl();
    }
}
