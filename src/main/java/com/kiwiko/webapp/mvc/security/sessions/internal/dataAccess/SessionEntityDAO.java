package com.kiwiko.webapp.mvc.security.sessions.internal.dataAccess;

import com.kiwiko.webapp.mvc.persistence.dataaccess.api.AuditableEntityManagerDAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.Collection;

public class SessionEntityDAO extends AuditableEntityManagerDAO<SessionEntity> {

    @Override
    protected Class<SessionEntity> getEntityType() {
        return SessionEntity.class;
    }

    @Override
    public SessionEntity save(SessionEntity entity) {
        Instant now = Instant.now();
        Instant endTime = entity.getEndTime();
        boolean isExpired = endTime != null && now.isAfter(endTime);

        if (isExpired && !entity.getIsRemoved()) {
            entity.setIsRemoved(true);
        }

        return super.save(entity);
    }

    public Collection<SessionEntity> getByUserId(long userId) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<SessionEntity> query = builder.createQuery(entityType);
        Root<SessionEntity> root = query.from(entityType);

        // Where user_id == userId
        Expression<Long> userIdField = root.get("userId");
        Predicate isUser = builder.equal(userIdField, userId);

        // Where not is_removed
        Expression<Boolean> isRemovedField = root.get("isRemoved");
        Predicate isActive = builder.isFalse(isRemovedField);

        query.where(isUser, isActive);
        return createQuery(query).getResultList();
    }

    public Collection<SessionEntity> getByTokens(Collection<String> tokens) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<SessionEntity> query = builder.createQuery(entityType);
        Root<SessionEntity> root = query.from(entityType);

        // Where token in tokens
        Expression<String> tokenField = root.get("token");
        Predicate hasToken = tokenField.in(tokens);

        // Where not is_removed
        Expression<Boolean> isRemovedField = root.get("isRemoved");
        Predicate isActive = builder.isFalse(isRemovedField);

        query.where(hasToken, isActive);
        return createQuery(query).getResultList();
    }
}
