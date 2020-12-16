package com.kiwiko.webapp.mvc.lifecycle.shutdown.api;

import javax.inject.Inject;
import java.util.function.Supplier;

public class ApplicationShutdownHookConfigurationCreator {

    @Inject private ApplicationShutdownHookRegistry applicationShutdownHookRegistry;

    public <ShutdownHook extends ApplicationShutdownHook> ShutdownHook create(Supplier<ShutdownHook> createShutdownHook) {
        ShutdownHook shutdownHook = createShutdownHook.get();
        applicationShutdownHookRegistry.register(shutdownHook);
        return shutdownHook;
    }
}
