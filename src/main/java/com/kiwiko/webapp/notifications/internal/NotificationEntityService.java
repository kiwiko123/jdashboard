package com.kiwiko.webapp.notifications.internal;

import com.kiwiko.webapp.mvc.persistence.crud.api.CreateReadUpdateDeleteService;
import com.kiwiko.webapp.notifications.api.NotificationService;
import com.kiwiko.webapp.notifications.api.queries.GetNotificationsQuery;
import com.kiwiko.webapp.notifications.data.Notification;
import com.kiwiko.webapp.notifications.internal.dataaccess.NotificationEntity;
import com.kiwiko.webapp.notifications.internal.dataaccess.NotificationEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class NotificationEntityService
        extends CreateReadUpdateDeleteService<NotificationEntity, Notification, NotificationEntityDAO, NotificationEntityMapper>
        implements NotificationService {

    @Inject private NotificationEntityDAO notificationEntityDAO;
    @Inject private NotificationEntityMapper notificationEntityMapper;

    @Override
    protected NotificationEntityDAO dataAccessObject() {
        return notificationEntityDAO;
    }

    @Override
    protected NotificationEntityMapper mapper() {
        return notificationEntityMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Queue<Notification> query(GetNotificationsQuery query) {
        return notificationEntityDAO.getByQuery(query).stream()
                .map(notificationEntityMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
