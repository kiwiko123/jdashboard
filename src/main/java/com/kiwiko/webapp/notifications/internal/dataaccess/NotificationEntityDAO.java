package com.kiwiko.webapp.notifications.internal.dataaccess;

import com.kiwiko.library.persistence.dataAccess.api.EntityManagerDAO;
import com.kiwiko.webapp.notifications.api.queries.GetNotificationsQuery;
import com.kiwiko.webapp.notifications.data.NotificationStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class NotificationEntityDAO extends EntityManagerDAO<NotificationEntity> {

    @Override
    protected Class<NotificationEntity> getEntityType() {
        return NotificationEntity.class;
    }

    @Override
    public void delete(NotificationEntity entity) {
        entity.setStatus(NotificationStatus.REMOVED);
        save(entity);
    }

    public List<NotificationEntity> getByQuery(GetNotificationsQuery queryParameters) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<NotificationEntity> query = builder.createQuery(entityType);
        Root<NotificationEntity> root = query.from(entityType);

        Expression<NotificationEntity> userIdField = root.get("userId");
        Predicate isUserId = builder.equal(userIdField, queryParameters.getUserId());
        query.where(isUserId);

        if (!queryParameters.getStatuses().isEmpty()) {
            Expression<NotificationStatus> statusField = root.get("status");
            Predicate hasStatus = statusField.in(queryParameters.getStatuses());
            query.where(hasStatus);
        }

        Expression<NotificationEntity> createdDateField = root.get("createdDate");
        Order ascendingCreatedDate = builder.asc(createdDateField);
        query.orderBy(ascendingCreatedDate);

        return getResultList(query);
    }
}
