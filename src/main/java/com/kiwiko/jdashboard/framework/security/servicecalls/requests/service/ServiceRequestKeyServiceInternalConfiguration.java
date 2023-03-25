package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScope;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalDatasourceConfiguration;
import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalEntityManagerProvider;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.ServiceRequestKeyEntityMapper;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.data.ServiceRequestKeyEntityDataAccessObject;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.ChangeDataCapturePersistenceConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PACKAGE)
public class ServiceRequestKeyServiceInternalConfiguration implements JdashboardDependencyConfiguration {

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
}
