package com.kiwiko.jdashboard.webapp.mvc.lifecycle.api;

import java.util.Set;

public interface LifeCycleHookRegistry {

    void register(LifeCycleHook hook);

    Set<StartupHook> getStartupHooks();

    Set<ShutdownHook> getShutdownHooks();

    void clean();
}
