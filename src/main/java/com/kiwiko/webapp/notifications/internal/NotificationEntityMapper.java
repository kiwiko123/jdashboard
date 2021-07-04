package com.kiwiko.webapp.notifications.internal;

import com.kiwiko.library.persistence.properties.api.EntityMapper;
import com.kiwiko.webapp.notifications.data.Notification;
import com.kiwiko.webapp.notifications.internal.dataaccess.NotificationEntity;

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
