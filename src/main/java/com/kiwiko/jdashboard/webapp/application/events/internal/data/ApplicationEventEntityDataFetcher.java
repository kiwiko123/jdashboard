package com.kiwiko.jdashboard.webapp.application.events.internal.data;

import com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces.DataAccessObject;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.parameters.ApplicationEventQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ApplicationEventEntityDataFetcher extends DataAccessObject<ApplicationEventEntity> {

    public List<ApplicationEventEntity> getByQuery(ApplicationEventQuery queryParameters) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<ApplicationEventEntity> query = builder.createQuery(entityType);
        Root<ApplicationEventEntity> root = query.from(entityType);

        Expression<String> eventTypeField = root.get("eventType");
        Predicate allCriteria = builder.equal(eventTypeField, queryParameters.getEventType());

        if (queryParameters.getEventKey() != null) {
            Expression<String> eventKeyField = root.get("eventKey");
            Predicate hasEventKey = builder.equal(eventKeyField, queryParameters.getEventKey());
            allCriteria = builder.and(allCriteria, hasEventKey);
        }

        if (queryParameters.getIsRemoved() != null) {
            Expression<Boolean> isRemovedField = root.get("isRemoved");
            Predicate isRemoved = builder.equal(isRemovedField, queryParameters.getIsRemoved());
            allCriteria = builder.and(allCriteria, isRemoved);
        }

        query.where(allCriteria);
        return createQuery(query).getResultList();
    }

    public List<ApplicationEventEntity> getByQueryLike(ApplicationEventQuery queryParameters) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<ApplicationEventEntity> query = builder.createQuery(entityType);
        Root<ApplicationEventEntity> root = query.from(entityType);

        Expression<String> eventTypeField = root.get("eventType");
        Predicate allCriteria = builder.like(eventTypeField, queryParameters.getEventType());

        if (queryParameters.getEventKey() != null) {
            Expression<String> eventKeyField = root.get("eventKey");
            Predicate hasEventKey = builder.equal(eventKeyField, queryParameters.getEventKey());
            allCriteria = builder.and(allCriteria, hasEventKey);
        }

        if (queryParameters.getIsRemoved() != null) {
            Expression<Boolean> isRemovedField = root.get("isRemoved");
            Predicate isRemoved = builder.equal(isRemovedField, queryParameters.getIsRemoved());
            allCriteria = builder.and(allCriteria, isRemoved);
        }

        query.where(allCriteria);
        return createQuery(query).getResultList();
    }
}
