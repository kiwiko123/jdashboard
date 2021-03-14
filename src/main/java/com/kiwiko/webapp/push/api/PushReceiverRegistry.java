package com.kiwiko.webapp.push.api;

import com.kiwiko.library.caching.api.ObjectCache;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PushReceiverRegistry {

    @Inject private ObjectCache objectCache;

    public void register(PushReceiver pushReceiver) {
        objectCache.cache(pushReceiver.getServiceId(), pushReceiver);
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
