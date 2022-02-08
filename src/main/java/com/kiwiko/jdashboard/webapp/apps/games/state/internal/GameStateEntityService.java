package com.kiwiko.jdashboard.webapp.apps.games.state.internal;

import com.google.gson.JsonSyntaxException;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.jdashboard.webapp.apps.games.state.api.parameters.FindGameStateParameters;
import com.kiwiko.jdashboard.webapp.apps.games.state.data.GameState;
import com.kiwiko.jdashboard.webapp.apps.games.state.data.GameType;
import com.kiwiko.jdashboard.webapp.apps.games.state.data.UserGameStateAssociation;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.data.GameStateEntity;
import com.kiwiko.jdashboard.webapp.apps.games.state.internal.data.GameStateEntityDataFetcher;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;
import com.kiwiko.jdashboard.webapp.persistence.services.crud.api.interfaces.CreateReadUpdateDeleteExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameStateEntityService implements GameStateService {

    @Inject private GameStateEntityDataFetcher dataFetcher;
    @Inject private GameStateEntityMapper mapper;
    @Inject private UserGameStateAssociationService userGameStateAssociationService;
    @Inject private CreateReadUpdateDeleteExecutor crudExecutor;
    @Inject private GsonProvider gsonProvider;
    @Inject private Logger logger;

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> findForGame(String gameType, long gameId) {
        return dataFetcher.findForGame(gameType, gameId)
                .map(mapper::toDto);
    }

    @Override
    public <T> Optional<T> reconstructGame(String gameType, long gameId, Class<T> gameStateClass) {
        String gameStateJson = findForGame(gameType, gameId)
                .flatMap(GameState::getGameStateJson)
                .orElse(null);
        if (gameStateJson == null) {
            return Optional.empty();
        }

        T result = null;
        try {
            result = gsonProvider.getDefault().fromJson(gameStateJson, gameStateClass);
        } catch (JsonSyntaxException e) {
            logger.error(String.format("Error converting game state JSON into class %s", gameStateClass.getName()), e);
        }

        return Optional.ofNullable(result);
    }

    @Transactional
    @Override
    public GameState saveGameState(GameState gameState) {
        GameStateEntity entity = mapper.toEntity(gameState);
        GameStateEntity managedEntity = dataFetcher.save(entity);
        return mapper.toDto(managedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public long getNewGameId(String gameType) {
        long maxGameId = dataFetcher.getMaxGameId(gameType)
                .orElse(0L);
        return maxGameId + 1;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> getById(long gameStateId) {
        return dataFetcher.getById(gameStateId)
                .map(mapper::toDto);
    }

    @Transactional
    @Override
    public GameState saveForUser(GameState gameState, long userId) {
        GameState savedGameState = saveGameState(gameState);
        if (findByGameStateAndUser(savedGameState.getId(), userId).isEmpty()) {
            UserGameStateAssociation association = new UserGameStateAssociation();
            association.setGameStateId(savedGameState.getId());
            association.setUserId(userId);
            userGameStateAssociationService.create(association);
        }

        return savedGameState;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> findByGameStateAndUser(long gameStateId, long userId) {
        return userGameStateAssociationService.findByGameStateAndUser(gameStateId, userId)
                .map(UserGameStateAssociation::getGameStateId)
                .flatMap(this::get);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<GameState> findGamesForUser(long userId, GameType gameType) {
        return dataFetcher.findByGameTypeAndUser(gameType, userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> findGameState(FindGameStateParameters parameters) {
        return dataFetcher.findIndividualByParameters(parameters)
                .map(mapper::toDto);
    }

    @Override
    public Optional<GameState> get(long id) {
        return crudExecutor.get(id, dataFetcher, mapper);
    }

    @Override
    public GameState create(GameState gameState) {
        return crudExecutor.create(gameState, dataFetcher, mapper);
    }

    @Override
    public GameState update(GameState gameState) {
        return crudExecutor.update(gameState, dataFetcher, mapper);
    }

    @Override
    public GameState merge(GameState gameState) {
        return crudExecutor.merge(gameState, dataFetcher, mapper);
    }
}
