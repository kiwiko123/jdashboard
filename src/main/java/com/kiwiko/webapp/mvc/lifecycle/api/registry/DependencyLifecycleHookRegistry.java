package com.kiwiko.webapp.mvc.lifecycle.api.registry;

import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHook;
import com.kiwiko.webapp.mvc.lifecycle.api.LifeCycleHookRegistry;
import com.kiwiko.webapp.mvc.lifecycle.api.ShutdownHook;
import com.kiwiko.webapp.mvc.lifecycle.api.StartupHook;
import com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup.SubscribePushServicesCreator;
import com.kiwiko.webapp.system.kafka.server.internal.KafkaServerShutdownHook;
import com.kiwiko.webapp.system.kafka.server.internal.KafkaServerStartupHook;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Set;

public class DependencyLifecycleHookRegistry implements LifeCycleHookRegistry {

    @Inject private SubscribePushServicesCreator subscribePushServicesCreator;
    @Inject private KafkaServerStartupHook kafkaServerStartupHook;
    @Inject private KafkaServerShutdownHook kafkaServerShutdownHook;

    @Override
    public void register(LifeCycleHook hook) {

    }

    @Override
    public Set<StartupHook> getStartupHooks() {
        return Set.of(subscribePushServicesCreator, kafkaServerStartupHook);
    }

    @Override
    public Set<ShutdownHook> getShutdownHooks() {
        return Set.of(kafkaServerShutdownHook);
    }

    @Override
    public void clean() {

    }
}
