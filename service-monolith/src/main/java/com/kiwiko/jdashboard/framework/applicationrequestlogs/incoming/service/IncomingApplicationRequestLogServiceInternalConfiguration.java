package com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service;

import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.interfaces.IncomingApplicationRequestLogService;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.IncomingApplicationRequestLogEntityMapper;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.IncomingApplicationRequestLogServiceImpl;
import com.kiwiko.jdashboard.framework.applicationrequestlogs.incoming.service.internal.data.IncomingApplicationRequestLogDataAccessObject;
import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalDatasourceConfiguration;
import com.kiwiko.jdashboard.framework.datasources.frameworkinternal.FrameworkInternalEntityManagerProvider;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScope;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.ChangeDataCapturePersistenceConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.data.cdc.internal.DataChangeCapturer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PACKAGE)
public class IncomingApplicationRequestLogServiceInternalConfiguration {

    @Bean
    public IncomingApplicationRequestLogEntityMapper incomingApplicationRequestLogEntityMapper() {
        return new IncomingApplicationRequestLogEntityMapper();
    }

    @Bean
    @ConfiguredBy({
            ChangeDataCapturePersistenceConfiguration.class,
            FrameworkInternalDatasourceConfiguration.class,
            LoggingConfiguration.class,
    })
    public IncomingApplicationRequestLogDataAccessObject incomingApplicationRequestLogDataAccessObject(
            FrameworkInternalEntityManagerProvider frameworkInternalEntityManagerProvider,
            DataChangeCapturer dataChangeCapturer,
            Logger logger) {
        return new IncomingApplicationRequestLogDataAccessObject(
                frameworkInternalEntityManagerProvider,
                dataChangeCapturer,
                logger);
    }
}
