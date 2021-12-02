package com.kiwiko.jdashboard.webapp.framework.security.sessions;

import com.kiwiko.jdashboard.webapp.framework.security.sessions.api.SessionHelper;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.api.SessionService;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.internal.SessionEntityMapper;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.internal.SessionEntityService;
import com.kiwiko.library.lang.random.TokenGenerator;
import com.kiwiko.jdashboard.webapp.framework.security.sessions.internal.dataAccess.SessionEntityDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfiguration {

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

    @Bean
    public SessionHelper sessionHelper() {
        return new SessionHelper();
    }
}
