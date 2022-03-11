package com.kiwiko.jdashboard.webapp.notifications.internal;

import com.kiwiko.jdashboard.library.persistence.properties.api.EntityMapper;
import com.kiwiko.jdashboard.webapp.notifications.data.Notification;
import com.kiwiko.jdashboard.webapp.notifications.internal.dataaccess.NotificationEntity;

public class NotificationEntityMapper extends EntityMapper<NotificationEntity, Notification> {

    @Override
    protected Class<NotificationEntity> getEntityType() {
        return NotificationEntity.class;
    }

    @Override
    protected Class<Notification> getDTOType() {
        return Notification.class;
    }
}
