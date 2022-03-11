package com.kiwiko.jdashboard.webapp.framework.security.clientsessions;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces.ClientSessionService;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal.ClientSessionEntityDataMapper;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal.ClientSessionEntityService;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.internal.data.ClientSessionEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.UniversalUniqueIdentifierConfiguration;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientSessionConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({UniversalUniqueIdentifierConfiguration.class, PersistenceServicesCrudConfiguration.class})
    public ClientSessionService clientSessionService() {
        return new ClientSessionEntityService();
    }

    @Bean
    public ClientSessionEntityDataFetcher clientSessionEntityDataFetcher() {
        return new ClientSessionEntityDataFetcher();
    }

    @Bean
    public ClientSessionEntityDataMapper clientSessionEntityDataMapper() {
        return new ClientSessionEntityDataMapper();
    }
}
