package com.kiwiko.jdashboard.usercredentials.client;

import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.UserCredentialClient;
import com.kiwiko.jdashboard.usercredentials.client.impl.http.UserCredentialHttpClient;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.tools.apiclient.configuration.JdashboardApiClientConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCredentialClientConfiguration {

    @Bean
    @ConfiguredBy({JdashboardApiClientConfiguration.class, LoggingConfiguration.class})
    public UserCredentialClient userCredentialClient() {
        return new UserCredentialHttpClient();
    }
}
