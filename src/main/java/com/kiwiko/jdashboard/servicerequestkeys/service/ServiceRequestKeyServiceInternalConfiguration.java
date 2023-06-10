package com.kiwiko.jdashboard.servicerequestkeys.service;

import com.kiwiko.jdashboard.framework.datasources.DefaultEntityManagerProvider;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.app.ServiceRequestKeyAppService;
import com.kiwiko.jdashboard.servicerequestkeys.service.internal.app.ServiceRequestKeyAppServiceImpl;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalDatasourceConfiguration;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.servicerequestkeys.service.internal.ServiceRequestKeyEntityMapper;
import com.kiwiko.jdashboard.servicerequestkeys.service.internal.data.ServiceRequestKeyEntityDataAccessObject;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.ChangeDataCapturePersistenceConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PACKAGE)
public class ServiceRequestKeyServiceInternalConfiguration {

    @Bean
    @ConfiguredBy({
            FrameworkInternalDatasourceConfiguration.class,
            LoggingConfiguration.class,
            ChangeDataCapturePersistenceConfiguration.class,
    })
    public ServiceRequestKeyEntityDataAccessObject serviceRequestKeyEntityDataAccessObject(
            DefaultEntityManagerProvider entityManagerProvider,
            DataChangeCapturer dataChangeCapturer,
            Logger logger) {
        return new ServiceRequestKeyEntityDataAccessObject(entityManagerProvider, dataChangeCapturer, logger);
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
