package com.kiwiko.webapp.push.api;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Singleton
public class PushServiceRegistry {

    private static final Map<String, Set<PushService>> pushServicesByIdentifier = new HashMap<>();

    public void register(PushService pushService) {
        pushServicesByIdentifier.computeIfAbsent(pushService.getServiceId(), id -> new HashSet<>())
                .add(pushService);
    }

    public void deregister(String identifier) {
        if (pushServicesByIdentifier.containsKey(identifier)) {
            pushServicesByIdentifier.get(identifier)
                    .removeIf(service -> Objects.equals(identifier, service.getServiceId()));
        }
    }

    public Set<PushService> getPushServicesForId(String identifier) {
        Set<PushService> result = pushServicesByIdentifier.getOrDefault(identifier, new HashSet<>());
        return Collections.unmodifiableSet(result);
    }
}
