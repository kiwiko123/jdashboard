package com.kiwiko.games.state.internal.dataAccess;

import com.kiwiko.games.state.data.GameType;
import com.kiwiko.persistence.dataAccess.api.AuditableEntityManagerDAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class GameStateEntityDAO extends AuditableEntityManagerDAO<GameStateEntity> {

    @Override
    protected Class<GameStateEntity> getEntityType() {
        return GameStateEntity.class;
    }

    public Optional<GameStateEntity> findForGame(GameType gameType, long gameId) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<GameStateEntity> query = builder.createQuery(entityType);
        Root<GameStateEntity> root = query.from(entityType);

        // Game type
        Expression<GameStateEntity> gameTypeExpression = root.get("gameType");
        Predicate hasGameType = builder.equal(gameTypeExpression, gameType);

        // Game id
        Expression<Long> gameIdExpression = root.get("gameId");
        Predicate hasGameId = builder.equal(gameIdExpression, gameId);

        Predicate matchesGame = builder.and(hasGameType, hasGameId);
        query.where(matchesGame);

        return getSingleResult(query);
    }
}
