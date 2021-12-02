package com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers;

import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.dto.RegisterPushServiceSubscriberParameters;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Singleton
public class PushServiceSubscriberRegistry {

    private final Map<String, Set<RegisterPushServiceSubscriberParameters>> registrationParametersByServiceId;

    public PushServiceSubscriberRegistry() {
        registrationParametersByServiceId = new HashMap<>();
    }

    public void register(RegisterPushServiceSubscriberParameters parameters) {
        Objects.requireNonNull(parameters.getServiceId(), "Service ID is required");
        Objects.requireNonNull(parameters.getPushServiceSubscriber(), "Push service subscriber is required");
        registrationParametersByServiceId.computeIfAbsent(parameters.getServiceId(), key -> new HashSet<>()).add(parameters);
    }

    public Collection<RegisterPushServiceSubscriberParameters> getByServiceId(String serviceId) {
        return registrationParametersByServiceId.getOrDefault(serviceId, Collections.emptySet());
    }
}
