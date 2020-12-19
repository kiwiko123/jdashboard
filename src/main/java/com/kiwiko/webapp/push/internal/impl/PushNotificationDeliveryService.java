package com.kiwiko.webapp.push.internal.impl;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.notifications.api.NotificationService;
import com.kiwiko.webapp.notifications.api.queries.GetNotificationsQuery;
import com.kiwiko.webapp.notifications.data.Notification;
import com.kiwiko.webapp.notifications.data.NotificationSource;
import com.kiwiko.webapp.notifications.data.NotificationStatus;
import com.kiwiko.webapp.push.api.parameters.OnPushReceivedParameters;
import com.kiwiko.webapp.push.api.parameters.PushToClientParameters;
import com.kiwiko.webapp.push.impl.TextWebSocketPushService;

import javax.inject.Inject;

public class PushNotificationDeliveryService extends TextWebSocketPushService {

    @Inject private NotificationService notificationService;
    @Inject private LogService logService;

    @Override
    public String getServiceId() {
        return "jdashboard-notifications";
    }

    @Override
    public boolean shouldPushToClient(PushToClientParameters parameters) {
        return true;
    }

    @Override
    public void onPushReceived(OnPushReceivedParameters parameters) {
        logService.debug(String.format("Push Notification Delivery Service received a push: %s", parameters.toString()));
    }

    public void sendPendingNotifications(long userId) {
        GetNotificationsQuery query = new GetNotificationsQuery()
                .setUserId(userId)
                .withStatus(NotificationStatus.SENT);

        for (Notification notification : notificationService.query(query)) {
            PushToClientParameters pushToClientParameters = new PushToClientParameters();
            pushToClientParameters.setRecipientUserId(userId);
            pushToClientParameters.setServiceId(getServiceId());
            pushToClientParameters.setData(notification.getContent());

            try {
                pushToClient(pushToClientParameters);
            } catch (Exception e) {
                logService.error(
                        String.format("Error sending pending push notification %s", pushToClientParameters.toString()),
                        e);
            }
        }
    }

    public void enqueueMissedNotification(PushToClientParameters parameters) {
        Notification notification = new Notification();
        notification.setUserId(parameters.getRecipientUserId());
        notification.setStatus(NotificationStatus.SENT);
        notification.setSource(NotificationSource.PUSH_SERVICE);
        notification.setContent(parameters.getData().toString());

        notificationService.create(notification);
    }
}
