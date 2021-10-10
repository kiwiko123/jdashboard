package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.dto;

import com.kiwiko.webapp.streaming.pushservice.api.interfaces.PushServiceSubscriber;

import java.util.Objects;

public class RegisterPushServiceSubscriberParameters {

    private final String serviceId;
    private final PushServiceSubscriber pushServiceSubscriber;

    public RegisterPushServiceSubscriberParameters(
            String serviceId,
            PushServiceSubscriber pushServiceSubscriber) {
        this.serviceId = serviceId;
        this.pushServiceSubscriber = pushServiceSubscriber;
    }

    public String getServiceId() {
        return serviceId;
    }

    public PushServiceSubscriber getPushServiceSubscriber() {
        return pushServiceSubscriber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterPushServiceSubscriberParameters that = (RegisterPushServiceSubscriberParameters) o;
        return serviceId.equals(that.serviceId) && pushServiceSubscriber.equals(that.pushServiceSubscriber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, pushServiceSubscriber);
    }
}
