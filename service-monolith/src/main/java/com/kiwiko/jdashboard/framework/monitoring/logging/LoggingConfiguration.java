package com.kiwiko.jdashboard.framework.monitoring.logging;

import com.kiwiko.jdashboard.framework.monitoring.logging.impl.StopGapSlf4jLogger;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {

    @Bean
    public Logger logger() {
        return new StopGapSlf4jLogger();
    }
}
