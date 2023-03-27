package com.kiwiko.jdashboard.framework.codeanalysis.spidi;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.external.SpiDiStartupHook;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScope;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ConfigurationScopeLevel;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiService;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.internal.SpiDiServiceImpl;
import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations.ConfiguredBy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationScope(ConfigurationScopeLevel.PUBLIC)
public class SpiDiExternalConfiguration {

    @Bean
    @ConfiguredBy(SpiDiInternalConfiguration.class)
    public SpiDiService spiDiService() {
        return new SpiDiServiceImpl();
    }

    @Bean
    @ConfiguredBy(SpiDiInternalConfiguration.class)
    public SpiDiStartupHook spiDiStartupHook() {
        return new SpiDiStartupHook();
    }
}
