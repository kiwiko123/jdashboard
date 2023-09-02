package com.kiwiko.jdashboard.permissions.client;

import com.kiwiko.jdashboard.permissions.client.impl.PermissionHttpClient;
import com.kiwiko.jdashboard.permissions.client.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.tools.apiclient.configuration.JdashboardApiClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionClientConfiguration {

    @Bean
    @ConfiguredBy({JdashboardApiClientConfiguration.class, LoggingConfiguration.class})
    public PermissionClient permissionClient() {
        return new PermissionHttpClient();
    }
}
