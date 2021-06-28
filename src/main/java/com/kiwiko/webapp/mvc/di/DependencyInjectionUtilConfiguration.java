package com.kiwiko.webapp.mvc.di;

import com.kiwiko.webapp.mvc.configuration.MvcConfigurationConfiguration;
import com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations.ConfiguredBy;
import com.kiwiko.webapp.mvc.di.api.interfaces.DependencyInstantiator;
import com.kiwiko.webapp.mvc.di.internal.SpringAnnotationConfigApplicationContextDependencyInstantiator;
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
