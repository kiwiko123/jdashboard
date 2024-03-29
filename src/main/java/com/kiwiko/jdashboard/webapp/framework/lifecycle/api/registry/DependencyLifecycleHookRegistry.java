package com.kiwiko.jdashboard.webapp.framework.lifecycle.api.registry;

import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.LifeCycleHook;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.LifeCycleHookRegistry;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.ShutdownHook;
import com.kiwiko.jdashboard.webapp.framework.lifecycle.api.StartupHook;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup.SubscribePushServicesCreator;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Set;

public class DependencyLifecycleHookRegistry implements LifeCycleHookRegistry {

    @Inject private SubscribePushServicesCreator subscribePushServicesCreator;

    @Override
    public void register(LifeCycleHook hook) {

    }

    @Override
    public Set<StartupHook> getStartupHooks() {
        return Set.of(subscribePushServicesCreator);
    }

    @Override
    public Set<ShutdownHook> getShutdownHooks() {
        return Collections.emptySet();
    }

    @Override
    public void clean() {

    }
}
