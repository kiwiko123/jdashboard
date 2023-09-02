package com.kiwiko.jdashboard.sessions.client;

import com.kiwiko.jdashboard.sessions.client.api.interfaces.SessionClient;
import com.kiwiko.jdashboard.sessions.client.impl.SessionHttpClient;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.tools.apiclient.configuration.JdashboardApiClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionClientConfiguration {

    @Bean
    @ConfiguredBy({JdashboardApiClientConfiguration.class})
    public SessionClient sessionClient() {
        return new SessionHttpClient();
    }
}
