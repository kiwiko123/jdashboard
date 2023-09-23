package com.kiwiko.jdashboard.framework.codeanalysis.spidi.external;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ResolveDependenciesInput;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiException;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiService;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHook;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHookException;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;

public class SpiDiStartupHook implements ApplicationStartupHook {

    @Inject private SpiDiService spiDiService;

    @Value("${jdashboard.static-code-analysis.spidi.enabled}") boolean enableSpiDiStaticCodeAnalysis;

    @Override
    public void run() throws ApplicationStartupHookException {
        if (!enableSpiDiStaticCodeAnalysis) {
            return;
        }

        ResolveDependenciesInput input = ResolveDependenciesInput.builder()
                .rootPackagePath("com.kiwiko.jdashboard")
                .build();

        try {
            spiDiService.resolveConfigurationDependencies(input);
        } catch (SpiDiException e) {
            throw new ApplicationStartupHookException("test", e);
        }
    }
}
