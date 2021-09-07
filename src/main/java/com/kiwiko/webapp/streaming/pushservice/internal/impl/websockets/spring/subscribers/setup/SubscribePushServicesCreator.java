package com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.setup;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.mvc.lifecycle.api.StartupHook;
import com.kiwiko.webapp.notifications.internal.push.NotificationPushServiceSubscriber;
import com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.PushServiceSubscriberRegistry;
import com.kiwiko.webapp.streaming.pushservice.internal.impl.websockets.spring.subscribers.dto.RegisterPushServiceSubscriberParameters;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SubscribePushServicesCreator implements StartupHook {

    @Inject private Logger logger;
    @Inject private PushServiceSubscriberRegistry pushServiceSubscriberRegistry;

    @Inject private NotificationPushServiceSubscriber notificationPushServiceSubscriber;

    @Override
    public void run() {
        pushServiceSubscriberRegistry
                .register(
                        new RegisterPushServiceSubscriberParameters(
                                NotificationPushServiceSubscriber.SERVICE_ID, notificationPushServiceSubscriber));
        ;
    }
}
