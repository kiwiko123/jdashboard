package com.kiwiko.webapp.reflection;

import com.kiwiko.library.lang.reflection.api.interfaces.ClassScanner;
import com.kiwiko.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.webapp.reflection.api.impl.SpringClassPathScanningClassScanner;
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
