package com.kiwiko.jdashboard.framework.lifecycle.startup.registry;

import com.kiwiko.jdashboard.framework.codeanalysis.spidi.external.SpiDiStartupHook;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHook;

import javax.inject.Inject;
import java.util.List;

public class ApplicationStartupChain {

    @Inject private SpiDiStartupHook spiDiStartupHook;

    public final List<ApplicationStartupHook> getStartupHooks() {
        return List.of();
//        return List.of(spiDiStartupHook);
    }
}
