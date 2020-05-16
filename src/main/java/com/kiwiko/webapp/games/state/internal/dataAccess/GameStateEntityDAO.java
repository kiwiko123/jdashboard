package com.kiwiko.webapp.games.state.internal.dataAccess;

import com.kiwiko.webapp.games.state.data.GameType;
import com.kiwiko.library.persistence.dataAccess.api.AuditableEntityManagerDAO;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

@Singleton
public class GameStateEntityDAO extends AuditableEntityManagerDAO<GameStateEntity> {

    @PersistenceContext
    private EntityManager entityManager;

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

    /**
     * Finds the current maximum game_id for the given {@link GameType}.
     * If no records with that game type exist, an empty optional is returned.
     *
     * @param gameType
     * @return the current maximum game_id for the given game type
     */
    public Optional<Long> getMaxGameId(GameType gameType) {
        CriteriaBuilder builder = criteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<GameStateEntity> root = query.from(entityType);

        Expression<Long> gameIdExpression = root.get("gameId");
        Expression<Long> maxGameId = builder.max(gameIdExpression);

        Expression<GameStateEntity> gameTypeExpression = root.get("gameType");
        Predicate hasGameType = builder.equal(gameTypeExpression, gameType);

        query.select(maxGameId)
                .where(hasGameType);

        Long maxGameIdValue = null;
        try {
            maxGameIdValue = entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            // do nothing
        }

        return Optional.ofNullable(maxGameIdValue);
    }

    public Collection<GameStateEntity> findForUser(long userId) {
        String queryString = "SELECT gs.* FROM game_states gs " +
                "JOIN user_game_state_associations ugsa ON gs.game_state_id = ugsa.game_state_id " +
                String.format("WHERE ugsa.user_id = %d ", userId) +
                "AND gs.is_removed = false;";

        return entityManager.createNativeQuery(queryString, GameStateEntity.class)
                .getResultList();
    }

    public Collection<GameStateEntity> findByGameTypeAndUser(GameType gameType, long userId) {
        String queryString = "SELECT gs.* FROM game_states gs " +
                "JOIN user_game_state_associations ugsa ON gs.game_state_id = ugsa.game_state_id " +
                String.format("WHERE ugsa.user_id = %d ", userId) +
                String.format("AND gs.game_type = '%s' ", gameType.toString()) +
                "AND gs.is_removed = false;";

        return entityManager.createNativeQuery(queryString, GameStateEntity.class)
                .getResultList();
    }
}
