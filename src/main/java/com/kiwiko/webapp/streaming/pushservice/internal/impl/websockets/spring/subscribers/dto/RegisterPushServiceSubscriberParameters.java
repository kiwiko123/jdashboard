package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.dto;

import com.kiwiko.webapp.streaming.pushservice.api.interfaces.PushServiceSubscriber;

import java.util.Objects;

public class RegisterPushServiceSubscriberParameters {

    private final String serviceId;
    private final Class<? extends PushServiceSubscriber> subscriberType;
    private final Class<?> baseSubscriberConfigurationType;

    public RegisterPushServiceSubscriberParameters(
            String serviceId,
            Class<? extends PushServiceSubscriber> subscriberType,
            Class<?> baseSubscriberConfigurationType) {
        this.serviceId = serviceId;
        this.subscriberType = subscriberType;
        this.baseSubscriberConfigurationType = baseSubscriberConfigurationType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public Class<? extends PushServiceSubscriber> getSubscriberType() {
        return subscriberType;
    }

    public Class<?> getBaseSubscriberConfigurationType() {
        return baseSubscriberConfigurationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterPushServiceSubscriberParameters that = (RegisterPushServiceSubscriberParameters) o;
        return serviceId.equals(that.serviceId) && subscriberType.equals(that.subscriberType) && Objects.equals(baseSubscriberConfigurationType, that.baseSubscriberConfigurationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, subscriberType, baseSubscriberConfigurationType);
    }
}
