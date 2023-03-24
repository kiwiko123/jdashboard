package com.kiwiko.jdashboard.framework.codeanalysis.spidi.external;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.SpiDiConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpiDiExternalConfiguration implements JdashboardDependencyConfiguration {

    @Bean
    @ConfiguredBy(SpiDiConfiguration.class)
    public SpiDiStartupHook spiDiStartupHook() {
        return new SpiDiStartupHook();
    }
}
