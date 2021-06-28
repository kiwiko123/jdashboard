package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers;

import com.kiwiko.webapp.streaming.pushservice.api.interfaces.PushServiceSubscriber;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Singleton
public class PushServiceSubscriberRegistry {

    private Map<String, Set<Class<? extends PushServiceSubscriber>>> subscribersByServiceId;

    public PushServiceSubscriberRegistry() {
        subscribersByServiceId = new HashMap<>();
    }

    public void register(String serviceId, Class<? extends PushServiceSubscriber> subscriberType) {
        subscribersByServiceId.computeIfAbsent(serviceId, key -> new HashSet<>()).add(subscriberType);
    }

    public Collection<Class<? extends PushServiceSubscriber>> getByServiceId(String serviceId) {
        return subscribersByServiceId.getOrDefault(serviceId, new HashSet<>());
    }
}
