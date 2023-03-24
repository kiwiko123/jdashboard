package com.kiwiko.jdashboard.framework.codeanalysis.spidi;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiService;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyConfigurationAnalyzer;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyInjectionInspector;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.DependencyResolver;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.SpiDiServiceImpl;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpiDiConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    public SpiDiService spiDiService() {
        return new SpiDiServiceImpl();
    }

    @Bean
    public DependencyConfigurationAnalyzer dependencyConfigurationResolver() {
        return new DependencyConfigurationAnalyzer();
    }

    @Bean
    public DependencyInjectionInspector dependencyInjectionInspector() {
        return new DependencyInjectionInspector();
    }

    @Bean
    public DependencyResolver dependencyResolver() {
        return new DependencyResolver();
    }
}
