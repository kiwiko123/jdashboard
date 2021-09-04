package com.kiwiko.webapp.monitoring.logging;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.library.metrics.impl.ConsoleLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {

    @Bean
    public Logger logger() {
        return new ConsoleLogger();
    }
}
