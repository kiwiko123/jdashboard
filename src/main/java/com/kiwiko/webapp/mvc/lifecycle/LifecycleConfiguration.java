package com.kiwiko.webapp.mvc.lifecycle;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.library.metrics.impl.ConsoleLogService;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHookConfigurationCreator;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHookRegistry;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleService;
import com.kiwiko.webapp.mvc.lifecycle.internal.ApplicationLifeCycleService;
import com.kiwiko.webapp.mvc.lifecycle.internal.LifeCycleHookRegistryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifecycleConfiguration {

    @Bean
    public LifeCycleHookRegistry lifeCycleHookRegistry() {
        return new LifeCycleHookRegistryHandler();
    }

    @Bean
    public LifeCycleHookConfigurationCreator lifeCycleHookConfigurationCreator() {
        return new LifeCycleHookConfigurationCreator();
    }

    @Bean
    public LifeCycleService lifeCycleService() {
        return new ApplicationLifeCycleService();
    }

    @Bean
    public LogService logService() {
        return new ConsoleLogService();
    }
}
