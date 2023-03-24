package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service;

import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalDatasourceConfiguration;
import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalEntityManagerProvider;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.interfaces.ServiceRequestKeyService;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.ServiceRequestKeyEntityMapper;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.ServiceRequestKeyServiceImpl;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.data.ServiceRequestKeyEntityDataAccessObject;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.ChangeDataCapturePersistenceConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.UniversalUniqueIdentifierConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceRequestKeyServiceConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({
            FrameworkInternalDatasourceConfiguration.class,
            LoggingConfiguration.class,
            ChangeDataCapturePersistenceConfiguration.class,
    })
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
    @ConfiguredBy({
            PersistenceServicesCrudConfiguration.class,
            TransactionConfiguration.class,
            UniversalUniqueIdentifierConfiguration.class,
    })
    public ServiceRequestKeyService serviceRequestKeyService() {
        return new ServiceRequestKeyServiceImpl();
    }
}
