package com.kiwiko.webapp.mvc.lifecycle.api;

import javax.inject.Inject;
import java.util.function.Supplier;

public class LifeCycleHookConfigurationCreator {

    @Inject private LifeCycleHookRegistry lifeCycleHookRegistry;

    public <Hook extends StartupHook> Hook createStartupHook(Supplier<Hook> createHook) {
        Hook hook = createHook.get();
        lifeCycleHookRegistry.register(hook);
        return hook;
    }

    public <Hook extends ShutdownHook> Hook createShutdownHook(Supplier<Hook> createHook) {
        Hook hook = createHook.get();
        lifeCycleHookRegistry.register(hook);
        return hook;
    }
}
