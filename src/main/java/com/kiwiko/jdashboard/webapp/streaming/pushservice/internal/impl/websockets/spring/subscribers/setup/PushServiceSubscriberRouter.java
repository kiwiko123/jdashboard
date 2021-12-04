package com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.framework.di.api.interfaces.DependencyInstantiator;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.dto.ClientPushRequest;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.PushServiceSubscriber;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters.OnPushReceivedParameters;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.PushServiceSubscriberRegistry;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.dto.RegisterPushServiceSubscriberParameters;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class PushServiceSubscriberRouter {

    @Inject private PushServiceSubscriberRegistry pushServiceSubscriberRegistry;
    @Inject private DependencyInstantiator dependencyInstantiator;
    @Inject private Logger logger;

    public void routeMessageToSubscribers(ClientPushRequest request, String message) {
        List<PushServiceSubscriber> subscribers = getSubscribers(request.getServiceId());
        for (PushServiceSubscriber subscriber : subscribers) {
            OnPushReceivedParameters parameters = new OnPushReceivedParameters();
            parameters.setUserId(request.getUserId());
            parameters.setRecipientUserId(request.getRecipientUserId());
            parameters.setServiceId(request.getServiceId());
            parameters.setMessage(message);

            try {
                subscriber.onPushReceived(parameters);
            } catch (Exception e) {
                logger.error(String.format("Error with push service subscriber %s receiving message %s", subscriber.getClass().getSimpleName(), message));
            }
        }
    }

    private List<PushServiceSubscriber> getSubscribers(String serviceId) {
        return pushServiceSubscriberRegistry.getByServiceId(serviceId).stream()
                .map(RegisterPushServiceSubscriberParameters::getPushServiceSubscriber)
                .collect(Collectors.toUnmodifiableList());
    }
}
