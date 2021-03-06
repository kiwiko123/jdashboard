package com.kiwiko.webapp.mvc.lifecycle.api.registry;

import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHook;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHookRegistry;
import com.kiwiko.webapp.mvc.lifecycle.api.ShutdownHook;
import com.kiwiko.webapp.mvc.lifecycle.api.StartupHook;
import com.kiwiko.webapp.push.internal.PushServiceShutdownHook;
import com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup.SubscribePushServicesCreator;

import javax.inject.Inject;
import java.util.Set;

public class DependencyLifecycleHookRegistry implements LifeCycleHookRegistry {

    @Inject private SubscribePushServicesCreator subscribePushServicesCreator;
    @Inject private PushServiceShutdownHook pushServiceShutdownHook;

    @Override
    public void register(LifeCycleHook hook) {

    }

    @Override
    public Set<StartupHook> getStartupHooks() {
        return Set.of(subscribePushServicesCreator);
    }

    @Override
    public Set<ShutdownHook> getShutdownHooks() {
        return Set.of(pushServiceShutdownHook);
    }

    @Override
    public void clean() {

    }
}
