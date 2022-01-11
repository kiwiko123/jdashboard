package com.kiwiko.jdashboard.webapp.permissions;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.permissions.api.interfaces.PermissionService;
import com.kiwiko.jdashboard.webapp.permissions.internal.PermissionEntityMapper;
import com.kiwiko.jdashboard.webapp.permissions.internal.PermissionEntityService;
import com.kiwiko.jdashboard.webapp.permissions.internal.data.PermissionEntityDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionsConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public PermissionService permissionService() {
        return new PermissionEntityService();
    }

    @Bean
    public PermissionEntityDataFetcher permissionEntityDataFetcher() {
        return new PermissionEntityDataFetcher();
    }

    @Bean
    public PermissionEntityMapper permissionEntityMapper() {
        return new PermissionEntityMapper();
    }
}
