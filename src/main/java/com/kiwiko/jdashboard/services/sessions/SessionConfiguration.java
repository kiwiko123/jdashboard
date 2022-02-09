package com.kiwiko.jdashboard.services.sessions;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionService;
import com.kiwiko.jdashboard.services.sessions.internal.SessionEntityMapper;
import com.kiwiko.jdashboard.services.sessions.internal.SessionEntityService;
import com.kiwiko.jdashboard.library.lang.random.TokenGenerator;
import com.kiwiko.jdashboard.services.sessions.internal.data.SessionEntityDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfiguration implements JdashboardDependencyConfiguration {

    @Bean
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
