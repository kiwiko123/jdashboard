package com.kiwiko.jdashboard.webapp.apps.games.state.internal.data;

import com.kiwiko.jdashboard.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class UserGameStateAssociationEntityDataFetcher extends EntityDataFetcher<UserGameStateAssociationEntity> {

    @Override
    protected Class<UserGameStateAssociationEntity> getEntityType() {
        return UserGameStateAssociationEntity.class;
    }

    public List<UserGameStateAssociationEntity> findForUser(long userId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<UserGameStateAssociationEntity> query = builder.createQuery(entityType);
        Root<UserGameStateAssociationEntity> root = query.from(entityType);

        Expression<Long> userIdField = root.get("userId");
        Predicate hasUserId = builder.equal(userIdField, userId);

        query.where(hasUserId);
        return createQuery(query).getResultList();
    }

    public Optional<UserGameStateAssociationEntity> findForGameStateAndUser(long gameStateId, long userId) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<UserGameStateAssociationEntity> query = builder.createQuery(entityType);
        Root<UserGameStateAssociationEntity> root = query.from(entityType);
        Expression<Long> gameStateIdField = root.get("gameStateId");
        Predicate equalsGameState = builder.equal(gameStateIdField, gameStateId);

        Expression<Long> userIdField = root.get("userId");
        Predicate equalsUserId = builder.equal(userIdField, userId);

        query.where(equalsGameState, equalsUserId);
        return getSingleResult(query);
    }
}
