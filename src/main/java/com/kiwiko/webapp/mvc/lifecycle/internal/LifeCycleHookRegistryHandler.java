package com.kiwiko.webapp.mvc.lifecycle.internal;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHook;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHookRegistry;
import com.kiwiko.webapp.mvc.lifecycle.api.ShutdownHook;
import com.kiwiko.webapp.mvc.lifecycle.api.StartupHook;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class LifeCycleHookRegistryHandler implements LifeCycleHookRegistry {

    private static final Set<LifeCycleHook> hooks = new HashSet<>();

    @Inject private LogService logService;

    @Override
    public void register(LifeCycleHook hook) {
        hooks.add(hook);
    }

    @Override
    public Set<StartupHook> getStartupHooks() {
        return hooks.stream()
                .filter(StartupHook.class::isInstance)
                .map(StartupHook.class::cast)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ShutdownHook> getShutdownHooks() {
        return hooks.stream()
                .filter(ShutdownHook.class::isInstance)
                .map(ShutdownHook.class::cast)
                .collect(Collectors.toSet());
    }

    @Override
    public void clean() {
        hooks.removeIf(StartupHook.class::isInstance);
    }
}
