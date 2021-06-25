package com.kiwiko.webapp.apps.games.state.internal;

import com.kiwiko.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.webapp.apps.games.state.data.GameState;
import com.kiwiko.webapp.apps.games.state.data.GameType;
import com.kiwiko.webapp.apps.games.state.data.UserGameStateAssociation;
import com.kiwiko.webapp.apps.games.state.internal.dataAccess.GameStateEntity;
import com.kiwiko.webapp.apps.games.state.internal.dataAccess.GameStateEntityDAO;
import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;
import com.kiwiko.webapp.mvc.json.api.errors.JsonException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameStateEntityService implements GameStateService {

    @Inject
    private GameStateEntityDAO gameStateEntityDAO;

    @Inject
    private GameStateEntityPropertyMapper mapper;

    @Inject
    private UserGameStateAssociationService userGameStateAssociationService;

    @Inject
    private JsonMapper jsonMapper;

    @Inject
    private LogService logService;

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> findForGame(GameType gameType, long gameId) {
        return gameStateEntityDAO.findForGame(gameType, gameId)
                .map(mapper::toDTO);
    }

    @Override
    public <T> Optional<T> reconstructGame(GameType gameType, long gameId, Class<T> gameStateClass) {
        String gameStateJson = findForGame(gameType, gameId)
                .flatMap(GameState::getGameStateJson)
                .orElse(null);
        if (gameStateJson == null) {
            return Optional.empty();
        }

        T result = null;
        try {
            result = jsonMapper.deserialize(gameStateJson, gameStateClass);
        } catch (JsonException e) {
            logService.error(String.format("Error converting game state JSON into class %s", gameStateClass.getName()), e);
        }

        return Optional.ofNullable(result);
    }

    @Transactional
    @Override
    public GameState saveGameState(GameState gameState) {
        GameStateEntity entity = mapper.toEntity(gameState);
        GameStateEntity managedEntity = gameStateEntityDAO.save(entity);
        return mapper.toDTO(managedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public long getNewGameId(GameType gameType) {
        long maxGameId = gameStateEntityDAO.getMaxGameId(gameType)
                .orElse(0l);
        return maxGameId + 1;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> getById(long gameStateId) {
        return gameStateEntityDAO.getById(gameStateId)
                .map(mapper::toDTO);
    }

    @Transactional
    @Override
    public GameState saveForUser(GameState gameState, long userId) {
        GameState savedGameState = saveGameState(gameState);
        if (!findByGameStateAndUser(savedGameState.getId(), userId).isPresent()) {
            userGameStateAssociationService.create(savedGameState.getId(), userId);
        }

        return savedGameState;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> findByGameStateAndUser(long gameStateId, long userId) {
        return userGameStateAssociationService.findByGameStateAndUser(gameStateId, userId)
                .map(UserGameStateAssociation::getGameState);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<GameState> findGamesForUser(long userId, GameType gameType) {
        return gameStateEntityDAO.findByGameTypeAndUser(gameType, userId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toSet());
    }
}
