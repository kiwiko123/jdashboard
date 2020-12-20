package com.kiwiko.webapp.games.state.internal.dataAccess;

import com.kiwiko.webapp.mvc.persistence.dataaccess.api.EntityManagerDAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

public class UserGameStateAssociationEntityDAO extends EntityManagerDAO<UserGameStateAssociationEntity> {

    @Override
    protected Class<UserGameStateAssociationEntity> getEntityType() {
        return UserGameStateAssociationEntity.class;
    }

    public Collection<UserGameStateAssociationEntity> findForUser(long userId) {
        CriteriaQuery<UserGameStateAssociationEntity> equalsUserId = selectWhereEqual("userId", userId);
        return createQuery(equalsUserId).getResultList();
    }

    public Optional<UserGameStateAssociationEntity> findForGameStateAndUser(long gameStateId, long userId) {
        CriteriaBuilder builder = criteriaBuilder();
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
