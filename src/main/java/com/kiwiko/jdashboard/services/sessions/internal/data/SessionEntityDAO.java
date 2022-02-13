package com.kiwiko.jdashboard.services.sessions.internal.data;

import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.GetSessionsInput;
import com.kiwiko.jdashboard.webapp.persistence.data.access.api.interfaces.DataAccessObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class SessionEntityDAO extends DataAccessObject<SessionEntity> {

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
        CriteriaBuilder builder = getCriteriaBuilder();
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
        CriteriaBuilder builder = getCriteriaBuilder();
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

    public List<SessionEntity> get(GetSessionsInput input) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SessionEntity> query = builder.createQuery(entityType);
        Root<SessionEntity> root = query.from(entityType);

        if (input.getSessionIds() != null) {
            Expression<Long> id = root.get("id");
            Predicate hasId = id.in(input.getSessionIds());
            query.where(hasId);
        }

        if (input.getTokens() != null) {
            // Where token in tokens
            Expression<String> token = root.get("token");
            Predicate hasToken = token.in(input.getTokens());
            query.where(hasToken);
        }

        if (input.getIsActive() != null) {
            Expression<Boolean> isRemovedField = root.get("isRemoved");
            Predicate isActive = builder.isFalse(isRemovedField);
            query.where(isActive);
        }

        return createQuery(query).getResultList();
    }
}
