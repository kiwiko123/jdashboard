package com.kiwiko.mvc.security.sessions;

import com.kiwiko.mvc.security.sessions.api.SessionHelper;
import com.kiwiko.mvc.security.sessions.api.SessionService;
import com.kiwiko.mvc.security.sessions.internal.SessionEntityMapper;
import com.kiwiko.mvc.security.sessions.internal.SessionEntityService;
import com.kiwiko.mvc.security.sessions.internal.SessionTokenHelper;
import com.kiwiko.mvc.security.sessions.internal.dataAccess.SessionEntityDAO;
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
    public SessionTokenHelper sessionTokenHelper() {
        return new SessionTokenHelper();
    }

    @Bean
    public SessionHelper sessionHelper() {
        return new SessionHelper();
    }
}
