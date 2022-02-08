package com.kiwiko.jdashboard.services.permissions;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.services.permissions.api.interfaces.PermissionService;
import com.kiwiko.jdashboard.services.permissions.internal.PermissionEntityMapper;
import com.kiwiko.jdashboard.services.permissions.internal.PermissionEntityService;
import com.kiwiko.jdashboard.services.permissions.internal.data.PermissionEntityDataFetcher;
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
