package com.kiwiko.jdashboard.webapp.mvc.lifecycle;

import com.kiwiko.jdashboard.webapp.mvc.lifecycle.api.LifeCycleHookConfigurationCreator;
import com.kiwiko.jdashboard.webapp.mvc.lifecycle.api.LifeCycleHookRegistry;
import com.kiwiko.jdashboard.webapp.mvc.lifecycle.api.LifeCycleService;
import com.kiwiko.jdashboard.webapp.mvc.lifecycle.api.registry.DependencyLifecycleHookRegistry;
import com.kiwiko.jdashboard.webapp.mvc.lifecycle.internal.ApplicationLifeCycleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = LifecycleConfiguration.class)
public class LifecycleConfiguration {

    @Bean
    public LifeCycleHookRegistry lifeCycleHookRegistry() {
        return new DependencyLifecycleHookRegistry();
    }

    @Bean
    public LifeCycleHookConfigurationCreator lifeCycleHookConfigurationCreator() {
        return new LifeCycleHookConfigurationCreator();
    }

    @Bean
    public LifeCycleService lifeCycleService() {
        return new ApplicationLifeCycleService();
    }
}
