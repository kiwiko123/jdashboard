package com.kiwiko.jdashboard.webapp.notifications.api;

import com.kiwiko.library.persistence.interfaces.api.CreateReadUpdateDeleteAPI;
import com.kiwiko.jdashboard.webapp.notifications.api.queries.GetNotificationsQuery;
import com.kiwiko.jdashboard.webapp.notifications.data.Notification;

import java.util.Queue;

public interface NotificationService extends CreateReadUpdateDeleteAPI<Notification> {

    Queue<Notification> query(GetNotificationsQuery query);
}
