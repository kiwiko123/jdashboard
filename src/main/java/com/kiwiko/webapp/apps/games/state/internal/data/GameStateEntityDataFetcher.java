package com.kiwiko.webapp.apps.games.state.internal.data;

import com.kiwiko.webapp.apps.games.state.api.errors.GameStateException;
import com.kiwiko.webapp.apps.games.state.api.parameters.FindGameStateParameters;
import com.kiwiko.webapp.apps.games.state.data.GameType;
import com.kiwiko.webapp.persistence.data.fetchers.api.interfaces.EntityDataFetcher;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Singleton
public class GameStateEntityDataFetcher extends EntityDataFetcher<GameStateEntity> {

    public Optional<GameStateEntity> findForGame(String gameType, long gameId) {
        CriteriaBuilder builder = getCriteriaBuilder();
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
    public Optional<Long> getMaxGameId(String gameType) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<GameStateEntity> root = query.from(entityType);

        Expression<Long> gameIdExpression = root.get("gameId");
        Expression<Long> maxGameId = builder.max(gameIdExpression);

        Expression<GameStateEntity> gameTypeExpression = root.get("gameType");
        Predicate hasGameType = builder.equal(gameTypeExpression, gameType);

        query.select(maxGameId)
                .where(hasGameType);

        return getSingleResult(query);
    }

    public List<GameStateEntity> findForUser(long userId) {
        String queryString = "SELECT gs.* FROM game_states gs " +
                "JOIN user_game_state_associations ugsa ON gs.game_state_id = ugsa.game_state_id " +
                String.format("WHERE ugsa.user_id = %d ", userId) +
                "AND gs.is_removed = false;";

        @SuppressWarnings("unchecked")
        List<GameStateEntity> results = createNativeQuery(queryString, GameStateEntity.class).getResultList();
        return results;
    }

    public List<GameStateEntity> findByGameTypeAndUser(GameType gameType, long userId) {
        String queryString = "SELECT gs.* FROM game_states gs " +
                "JOIN user_game_state_associations ugsa ON gs.game_state_id = ugsa.game_state_id " +
                String.format("WHERE ugsa.user_id = %d ", userId) +
                String.format("AND gs.game_type = '%s' ", gameType.toString()) +
                "AND gs.is_removed = false;";

        @SuppressWarnings("unchecked")
        List<GameStateEntity> results = createNativeQuery(queryString, GameStateEntity.class).getResultList();
        return results;
    }

    public Optional<GameStateEntity> findIndividualByParameters(FindGameStateParameters parameters) {
        StringBuilder queryBuilder = new StringBuilder("SELECT gs.* FROM game_states gs ");

        if (parameters.getUserId() != null) {
            queryBuilder.append("JOIN user_game_state_associations ugsa ON gs.id = ugsa.game_state_id ");
        }

        List<String> conditions = new LinkedList<>();
        if (parameters.getUserId() != null) {
            String statement = String.format("ugsa.user_id = %d", parameters.getUserId());
            conditions.add(statement);
        }

        if (parameters.getGameId() != null) {
            String statement = String.format("gs.game_id = %d", parameters.getGameId());
            conditions.add(statement);
        }

        if (parameters.getGameType() != null) {
            String statement = String.format("gs.game_type = '%s'", parameters.getGameType());
            conditions.add(statement);
        }

        if (!conditions.isEmpty()) {
            String whereClause = String.join(" AND ", conditions);;
            queryBuilder.append("WHERE ").append(whereClause);
        }

        queryBuilder.append(';');

        @SuppressWarnings("unchecked")
        List<GameStateEntity> results = createNativeQuery(queryBuilder.toString(), GameStateEntity.class).getResultList();
        if (results.size() > 1) {
            throw new GameStateException(String.format("%d entities found for parameters %s", results.size(), parameters));
        }

        return results.stream().findFirst();
    }
}
