package com.kiwiko.jdashboard.webapp.framework.security.environments;

import com.kiwiko.jdashboard.webapp.framework.security.environments.api.EnvironmentService;
import com.kiwiko.jdashboard.webapp.framework.security.environments.internal.WebApplicationEnvironmentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = EnvironmentConfiguration.class)
public class EnvironmentConfiguration {

    @Bean
    public EnvironmentService environmentService() {
        return new WebApplicationEnvironmentService();
    }
}
