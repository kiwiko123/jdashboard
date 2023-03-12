package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service;

import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalEntityManagerProvider;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.interfaces.ServiceRequestKeyService;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.ServiceRequestKeyEntityMapper;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.ServiceRequestKeyServiceImpl;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.data.ServiceRequestKeyEntityDataAccessObject;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceRequestKeyServiceConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public ServiceRequestKeyEntityDataAccessObject serviceRequestKeyEntityDataAccessObject(
            FrameworkInternalEntityManagerProvider entityManagerProvider,
            DataChangeCapturer dataChangeCapturer,
            Logger logger) {
        return new ServiceRequestKeyEntityDataAccessObject(entityManagerProvider, dataChangeCapturer, logger);
    }

    @Bean
    public ServiceRequestKeyEntityMapper serviceRequestKeyEntityMapper() {
        return new ServiceRequestKeyEntityMapper();
    }

    @Bean
    public ServiceRequestKeyService serviceRequestKeyService() {
        return new ServiceRequestKeyServiceImpl();
    }
}
