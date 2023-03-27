package com.kiwiko.jdashboard.webapp.framework.lifecycle;

import com.kiwiko.jdashboard.framework.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.LifeCycleHookConfigurationCreator;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.LifeCycleHookRegistry;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.LifeCycleService;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.registry.DependencyLifecycleHookRegistry;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.internal.ApplicationLifeCycleService;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.PushServiceWebSocketConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = LifecycleConfiguration.class)
public class LifecycleConfiguration {

    @Bean
    @ConfiguredBy(PushServiceWebSocketConfiguration.class)
    public LifeCycleHookRegistry lifeCycleHookRegistry() {
        return new DependencyLifecycleHookRegistry();
    }

    @Bean
    public LifeCycleHookConfigurationCreator lifeCycleHookConfigurationCreator() {
        return new LifeCycleHookConfigurationCreator();
    }

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public LifeCycleService lifeCycleService() {
        return new ApplicationLifeCycleService();
    }
}
