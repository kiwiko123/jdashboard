package com.kiwiko.jdashboard.webapp.notifications.internal.push;

import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.PushServiceSubscriber;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters.OnPushReceivedParameters;

import javax.inject.Inject;

public class NotificationPushServiceSubscriber implements PushServiceSubscriber {
    public static final String SERVICE_ID = "jdashboard-notifications";

    @Inject private Logger logger;

    @Override
    public void onPushReceived(OnPushReceivedParameters parameters) {
        logger.debug("Notifications subscriber received!");
    }
}
