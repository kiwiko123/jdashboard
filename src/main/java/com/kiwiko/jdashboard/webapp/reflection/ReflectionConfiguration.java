package com.kiwiko.jdashboard.webapp.reflection;

import com.kiwiko.library.lang.reflection.api.interfaces.ClassScanner;
import com.kiwiko.jdashboard.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.reflection.api.impl.SpringClassPathScanningClassScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReflectionConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public ClassScanner classScanner() {
        return new SpringClassPathScanningClassScanner();
    }
}
