package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.di.api.interfaces.DependencyInstantiator;
import com.kiwiko.webapp.mvc.di.api.interfaces.exceptions.DependencyInstantiationException;
import com.kiwiko.webapp.streaming.pushservice.api.dto.ClientPushRequest;
import com.kiwiko.webapp.streaming.pushservice.api.interfaces.PushServiceSubscriber;
import com.kiwiko.webapp.streaming.pushservice.api.interfaces.parameters.OnPushReceivedParameters;
import com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.PushServiceSubscriberRegistry;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PushServiceSubscriberRouter {

    @Inject private PushServiceSubscriberRegistry pushServiceSubscriberRegistry;
    @Inject private DependencyInstantiator dependencyInstantiator;
    @Inject private Logger logger;

    public void routeMessageToSubscribers(ClientPushRequest request, String message) {
        List<PushServiceSubscriber> subscribers = instantiateSubscribers(request.getServiceId());
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

    private List<PushServiceSubscriber> instantiateSubscribers(String serviceId) {
        List<PushServiceSubscriber> subscribers = new ArrayList<>();
        Collection<Class<? extends PushServiceSubscriber>> subscriberTypes = pushServiceSubscriberRegistry.getByServiceId(serviceId);

        for (Class<? extends PushServiceSubscriber> subscriberType : subscriberTypes) {
            PushServiceSubscriber subscriber;
            try {
                subscriber = dependencyInstantiator.instantiateDependency(subscriberType);
            } catch (DependencyInstantiationException e) {
                logger.error(String.format("Error instantiating push service subscriber %s", subscriberType.getName()), e);
                continue;
            }

            subscribers.add(subscriber);
        }

        return subscribers;
    }
}
