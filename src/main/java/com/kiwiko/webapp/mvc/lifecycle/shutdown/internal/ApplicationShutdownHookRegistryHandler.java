package com.kiwiko.webapp.mvc.lifecycle.shutdown.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ApplicationShutdownHook;
import com.kiwiko.webapp.mvc.lifecycle.shutdown.api.ApplicationShutdownHookRegistry;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class ApplicationShutdownHookRegistryHandler implements ApplicationShutdownHookRegistry {

    private static final Set<ApplicationShutdownHook> shutdownHooks = new HashSet<>();

    @Inject private LogService logService;

    @Override
    public void register(ApplicationShutdownHook shutdownHook) {
        shutdownHooks.add(shutdownHook);
    }

    @Override
    public void shutdown() {
        for (ApplicationShutdownHook shutdownHook : shutdownHooks) {
            String shutdownHookName = shutdownHook.getClass().getName();
            logService.debug(String.format("Beginning shutdown hook %s", shutdownHookName));
            try {
                shutdownHook.run();
                logService.debug("Completed shutdown hook");
            } catch (Exception e) {
                logService.error(String.format("%s failed", shutdownHookName), e);
            }
        }
    }
}
