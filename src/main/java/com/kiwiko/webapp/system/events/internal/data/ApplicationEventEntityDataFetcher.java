package com.kiwiko.webapp.system.events.internal.data;

import com.kiwiko.webapp.mvc.persistence.dataaccess.api.EntityManagerDAO;
import com.kiwiko.webapp.system.events.api.interfaces.parameters.ApplicationEventQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ApplicationEventEntityDataFetcher extends EntityManagerDAO<ApplicationEventEntity> {

    @Override
    protected Class<ApplicationEventEntity> getEntityType() {
        return ApplicationEventEntity.class;
    }

    public List<ApplicationEventEntity> getByQuery(ApplicationEventQuery queryParameters) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<ApplicationEventEntity> query = builder.createQuery(entityType);
        Root<ApplicationEventEntity> root = query.from(entityType);

        Expression<String> eventTypeField = root.get("eventType");
        Predicate allCriteria = builder.equal(eventTypeField, queryParameters.getEventType());

        if (queryParameters.getEventKey() != null) {
            Expression<String> eventKeyField = root.get("eventKey");
            Predicate hasEventKey = builder.equal(eventKeyField, queryParameters.getEventKey());
            allCriteria = builder.and(allCriteria, hasEventKey);
        }

        query.where(allCriteria);
        return getResultList(query);
    }
}
