package com.kiwiko.webapp.mvc.lifecycle;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.library.metrics.impl.ConsoleLogService;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ApplicationShutdownHookConfigurationCreator;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ApplicationShutdownHookRegistry;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ShutdownService;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.internal.ApplicationShutdownHookRegistryHandler;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.internal.ApplicationShutdownService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifecycleConfiguration {

    @Bean
    public ApplicationShutdownHookRegistry applicationShutdownHookRegistry() {
        return new ApplicationShutdownHookRegistryHandler();
    }

    @Bean
    public ApplicationShutdownHookConfigurationCreator applicationShutdownHookConfigurationCreator() {
        return new ApplicationShutdownHookConfigurationCreator();
    }

    @Bean
    public ShutdownService shutdownService() {
        return new ApplicationShutdownService();
    }

    @Bean
    public LogService logService() {
        return new ConsoleLogService();
    }
}
