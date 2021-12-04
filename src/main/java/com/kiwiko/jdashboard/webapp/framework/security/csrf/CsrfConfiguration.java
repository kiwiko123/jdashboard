package com.kiwiko.jdashboard.webapp.framework.security.csrf;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.framework.application.properties.PropertiesConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.security.csrf.interceptors.CrossSiteRequestForgeryPreventionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CsrfConfiguration.class)
public class CsrfConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy({PropertiesConfiguration.class, LoggingConfiguration.class})
    public CrossSiteRequestForgeryPreventionInterceptor crossSiteRequestForgeryPreventionInterceptor() {
        return new CrossSiteRequestForgeryPreventionInterceptor();
    }
}
