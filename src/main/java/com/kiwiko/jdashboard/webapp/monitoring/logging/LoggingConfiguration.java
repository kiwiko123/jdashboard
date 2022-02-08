package com.kiwiko.jdashboard.webapp.monitoring.logging;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.library.metrics.impl.ConsoleLogger;
import com.kiwiko.jdashboard.webapp.monitoring.logging.impl.ConfigurationLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public Logger logger() {
        return new ConfigurationLogger();
//        return new ConsoleLogger();
    }

    @Bean
    public ConsoleLogger consoleLogger() {
        return new ConsoleLogger();
    }
}
