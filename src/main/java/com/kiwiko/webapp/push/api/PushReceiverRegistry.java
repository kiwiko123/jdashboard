package com.kiwiko.webapp.push.api;

import com.kiwiko.library.caching.api.CacheService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PushReceiverRegistry {

    @Inject private CacheService cacheService;

    public void register(PushReceiver pushReceiver) {
        cacheService.cache(pushReceiver.getServiceId(), pushReceiver);
    }

    public void deregister(String serviceId) {
        cacheService.discard(serviceId);
    }

    public Collection<PushReceiver> getPushReceiversForService(String serviceId) {
        List<PushReceiver> receivers = cacheService.<List<PushReceiver>>get(serviceId)
                .orElseGet(ArrayList::new);
        return Collections.unmodifiableList(receivers);
    }
}
