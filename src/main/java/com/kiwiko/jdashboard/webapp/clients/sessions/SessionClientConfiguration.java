package com.kiwiko.jdashboard.webapp.clients.sessions;

import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.SessionClient;
import com.kiwiko.jdashboard.webapp.clients.sessions.impl.SessionHttpClient;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.http.client.JdashboardApiClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionClientConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({JdashboardApiClientConfiguration.class})
    public SessionClient sessionClient() {
        return new SessionHttpClient();
    }
}
