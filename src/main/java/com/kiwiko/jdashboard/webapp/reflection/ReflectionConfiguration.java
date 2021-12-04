package com.kiwiko.jdashboard.webapp.reflection;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.library.lang.reflection.api.interfaces.ClassScanner;
import com.kiwiko.jdashboard.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.reflection.api.impl.SpringClassPathScanningClassScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReflectionConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(LoggingConfiguration.class)
    public ClassScanner classScanner() {
        return new SpringClassPathScanningClassScanner();
    }
}
