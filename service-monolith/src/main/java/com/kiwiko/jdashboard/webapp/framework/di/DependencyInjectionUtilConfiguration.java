package com.kiwiko.jdashboard.webapp.framework.di;

import com.kiwiko.jdashboard.webapp.framework.configuration.MvcConfigurationConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.framework.di.api.interfaces.DependencyInstantiator;
import com.kiwiko.jdashboard.webapp.framework.di.internal.SpringAnnotationConfigApplicationContextDependencyInstantiator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DependencyInjectionUtilConfiguration.class)
public class DependencyInjectionUtilConfiguration {

    @Bean
    @ConfiguredBy(MvcConfigurationConfiguration.class)
    public DependencyInstantiator dependencyInstantiator() {
        return new SpringAnnotationConfigApplicationContextDependencyInstantiator();
    }
}
