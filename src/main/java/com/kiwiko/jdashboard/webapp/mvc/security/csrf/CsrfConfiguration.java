package com.kiwiko.jdashboard.webapp.mvc.security.csrf;

import com.kiwiko.jdashboard.webapp.monitoring.logging.LoggingConfiguration;
import com.kiwiko.jdashboard.webapp.mvc.application.properties.PropertiesConfiguration;
import com.kiwiko.jdashboard.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.mvc.security.csrf.interceptors.CrossSiteRequestForgeryPreventionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CsrfConfiguration.class)
public class CsrfConfiguration {

    @Bean
    @ConfiguredBy({PropertiesConfiguration.class, LoggingConfiguration.class})
    public CrossSiteRequestForgeryPreventionInterceptor crossSiteRequestForgeryPreventionInterceptor() {
        return new CrossSiteRequestForgeryPreventionInterceptor();
    }
}
