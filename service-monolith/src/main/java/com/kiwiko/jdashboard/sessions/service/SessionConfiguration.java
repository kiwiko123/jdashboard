package com.kiwiko.jdashboard.sessions.service;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.framework.persistence.transactions.TransactionConfiguration;
import com.kiwiko.jdashboard.sessions.service.api.interfaces.SessionService;
import com.kiwiko.jdashboard.sessions.service.internal.SessionEntityMapper;
import com.kiwiko.jdashboard.sessions.service.internal.SessionEntityService;
import com.kiwiko.jdashboard.library.lang.random.TokenGenerator;
import com.kiwiko.jdashboard.sessions.service.internal.data.SessionEntityDAO;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.PersistenceServicesCrudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfiguration {

    @Bean
    @ConfiguredBy({PersistenceServicesCrudConfiguration.class, TransactionConfiguration.class, LoggingConfiguration.class})
    public SessionService sessionService() {
        return new SessionEntityService();
    }

    @Bean
    public SessionEntityDAO sessionEntityDAO() {
        return new SessionEntityDAO();
    }

    @Bean
    public SessionEntityMapper sessionEntityMapper() {
        return new SessionEntityMapper();
    }

    @Bean
    public TokenGenerator sessionTokenHelper() {
        return new TokenGenerator();
    }
}
