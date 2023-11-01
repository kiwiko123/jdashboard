package com.kiwiko.jdashboard.webapp.notifications.internal;

import com.kiwiko.jdashboard.webapp.framework.persistence.crud.api.CreateReadUpdateDeleteService;
import com.kiwiko.jdashboard.webapp.notifications.api.NotificationService;
import com.kiwiko.jdashboard.webapp.notifications.api.queries.GetNotificationsQuery;
import com.kiwiko.jdashboard.webapp.notifications.data.Notification;
import com.kiwiko.jdashboard.webapp.notifications.internal.dataaccess.NotificationEntity;
import com.kiwiko.jdashboard.webapp.notifications.internal.dataaccess.NotificationEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class NotificationEntityService
        extends CreateReadUpdateDeleteService<NotificationEntity, Notification, NotificationEntityDAO, NotificationEntityMapper>
        implements NotificationService {

    @Inject private NotificationEntityDAO notificationEntityDAO;
    @Inject private NotificationEntityMapper notificationEntityMapper;

    @Override
    protected NotificationEntityDAO getDataFetcher() {
        return notificationEntityDAO;
    }

    @Override
    protected NotificationEntityMapper getMapper() {
        return notificationEntityMapper;
    }

    @Transactional
    @Override
    public <R extends Notification> Notification create(R obj) {
        obj.setCreatedDate(Instant.now());
        return super.create(obj);
    }

    @Transactional(readOnly = true)
    @Override
    public Queue<Notification> query(GetNotificationsQuery query) {
        return notificationEntityDAO.getByQuery(query).stream()
                .map(notificationEntityMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
