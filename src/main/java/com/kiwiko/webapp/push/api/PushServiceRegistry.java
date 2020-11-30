package com.kiwiko.webapp.push.api;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Singleton
public class PushServiceRegistry {

    private static Map<String, Set<PushService>> pushServicesByIdentifier;

    public PushServiceRegistry() {
        pushServicesByIdentifier = new HashMap<>();
    }

    public void register(String identifier, PushService pushService) {
        pushServicesByIdentifier.computeIfAbsent(identifier, id -> new HashSet<>())
                .add(pushService);
    }

    public void deregister(String identifier, PushService pushService) {
        if (pushServicesByIdentifier.containsKey(identifier)) {
            pushServicesByIdentifier.get(identifier).remove(pushService);
        }
    }

    public Set<PushService> getPushServicesForId(String identifier) {
        Set<PushService> result = pushServicesByIdentifier.getOrDefault(identifier, new HashSet<>());
        return Collections.unmodifiableSet(result);
    }
}
