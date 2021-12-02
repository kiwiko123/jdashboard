package com.kiwiko.jdashboard.webapp.mvc.di;

import com.kiwiko.jdashboard.webapp.mvc.configuration.MvcConfigurationConfiguration;
import com.kiwiko.jdashboard.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.jdashboard.webapp.mvc.di.api.interfaces.DependencyInstantiator;
import com.kiwiko.jdashboard.webapp.mvc.di.internal.SpringAnnotationConfigApplicationContextDependencyInstantiator;
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
