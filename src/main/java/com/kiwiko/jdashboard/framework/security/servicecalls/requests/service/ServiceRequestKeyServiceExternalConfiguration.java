package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScope;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.interfaces.ServiceRequestKeyService;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.internal.ServiceRequestKeyServiceImpl;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.UniversalUniqueIdentifierConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PUBLIC)
public class ServiceRequestKeyServiceExternalConfiguration {

    @Bean
    @ConfiguredBy({
            ServiceRequestKeyServiceInternalConfiguration.class,
            PersistenceServicesCrudConfiguration.class,
            TransactionConfiguration.class,
            UniversalUniqueIdentifierConfiguration.class,
    })
    public ServiceRequestKeyService serviceRequestKeyService() {
        return new ServiceRequestKeyServiceImpl();
    }
}
