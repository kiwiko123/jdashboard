package com.kiwiko.jdashboard.framework.codeanalysis.spidi.external;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.ResolveDependenciesInput;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiException;
import com.kiwiko.jdashboard.framework.codeanalysis.spidi.interfaces.SpiDiService;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHook;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHookException;

import javax.inject.Inject;

public class SpiDiStartupHook implements ApplicationStartupHook {

    @Inject private SpiDiService spiDiService;


    @Override
    public void run() throws ApplicationStartupHookException {
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
