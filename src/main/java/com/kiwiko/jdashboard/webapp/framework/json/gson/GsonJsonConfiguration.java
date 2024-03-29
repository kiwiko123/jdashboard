package com.kiwiko.jdashboard.webapp.framework.json.gson;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = GsonJsonConfiguration.class)
public class GsonJsonConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public GsonProvider gsonProvider() {
        return new GsonProvider();
    }
}
