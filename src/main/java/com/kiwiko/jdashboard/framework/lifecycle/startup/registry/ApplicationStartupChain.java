package com.kiwiko.jdashboard.framework.lifecycle.startup.registry;

import com.kiwiko.jdashboard.framework.codeanalysis.configurations.impl.DependencyInjectedConfigurationResolver;
import com.kiwiko.jdashboard.framework.lifecycle.startup.api.interfaces.ApplicationStartupHook;

import javax.inject.Inject;
import java.util.List;

public class ApplicationStartupChain {

    @Inject private DependencyInjectedConfigurationResolver dependencyInjectedConfigurationResolver;

    public final List<ApplicationStartupHook> getStartupHooks() {
        return List.of(dependencyInjectedConfigurationResolver);
    }
}
