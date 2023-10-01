package com.kiwiko.jdashboard.framework.codeanalysis.spidi.external;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ResolveDependenciesInput;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiException;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiService;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHookException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import javax.inject.Inject;

public class SpiDiStartupHook implements ApplicationListener<ApplicationReadyEvent> {

    @Inject private SpiDiService spiDiService;

    @Value("${jdashboard.static-code-analysis.spidi.enabled}") boolean enableSpiDiStaticCodeAnalysis;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!enableSpiDiStaticCodeAnalysis) {
            return;
        }

        ResolveDependenciesInput input = ResolveDependenciesInput.builder()
                .rootPackagePath("com.kiwiko.jdashboard")
                .build();

        try {
            spiDiService.resolveConfigurationDependencies(input);
        } catch (SpiDiException e) {
            throw new RuntimeException("test", e);
        }
    }
}
