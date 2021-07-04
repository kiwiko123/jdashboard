package com.kiwiko.webapp.push.api;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PushReceiverRegistry {

    @Inject private ObjectCache objectCache;
    @Inject private Logger logger;

    public void register(PushReceiver pushReceiver) {
        String serviceId = pushReceiver.getServiceId();
        if (objectCache.get(serviceId).isPresent()) {
            logger.warn(String.format("Attempting to register duplicate push receiver %s", serviceId));
            return;
        }

        objectCache.cache(serviceId, pushReceiver);
    }

    public void deregister(String serviceId) {
        objectCache.invalidate(serviceId);
    }

    public Collection<PushReceiver> getPushReceiversForService(String serviceId) {
        List<PushReceiver> receivers = objectCache.<List<PushReceiver>>get(serviceId)
                .orElseGet(ArrayList::new);
        return Collections.unmodifiableList(receivers);
    }
}
