package com.kiwiko.jdashboard.webapp.clients.permissions;

import com.kiwiko.jdashboard.webapp.clients.permissions.impl.PermissionHttpClient;
import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.http.client.JdashboardHttpClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionClientConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(JdashboardHttpClientConfiguration.class)
    public PermissionClient permissionClient() {
        return new PermissionHttpClient();
    }
}