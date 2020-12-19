package com.kiwiko.webapp.notifications.api;

import com.kiwiko.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.webapp.notifications.api.queries.GetNotificationsQuery;
import com.kiwiko.webapp.notifications.data.Notification;

import java.util.Queue;

public interface NotificationService extends CreateReadUpdateDeleteAPI<Notification> {

    Queue<Notification> query(GetNotificationsQuery query);
}
