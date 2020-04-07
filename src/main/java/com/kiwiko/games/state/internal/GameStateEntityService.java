package com.kiwiko.games.state.internal;

import com.kiwiko.games.state.api.GameStateService;
import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.data.GameType;
import com.kiwiko.games.state.internal.dataAccess.GameStateEntity;
import com.kiwiko.games.state.internal.dataAccess.GameStateEntityDAO;
import com.kiwiko.metrics.api.LogService;
import com.kiwiko.mvc.json.api.JsonMapper;
import com.kiwiko.mvc.json.api.errors.JsonException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

public class GameStateEntityService implements GameStateService {

    @Inject
    private GameStateEntityDAO gameStateEntityDAO;

    @Inject
    private GameStateEntityPropertyMapper mapper;

    @Inject
    private JsonMapper jsonMapper;

    @Inject
    private LogService logService;

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> findForGame(GameType gameType, long gameId) {
        return gameStateEntityDAO.findForGame(gameType, gameId)
                .map(mapper::toTargetType);
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
        GameStateEntity entity = mapper.toSourceType(gameState);
        GameStateEntity managedEntity = gameStateEntityDAO.save(entity);
        return mapper.toTargetType(managedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public long getNewGameId(GameType gameType) {
        long maxGameId = gameStateEntityDAO.getMaxGameId(gameType)
                .orElse(0l);
        return maxGameId + 1;
    }
}
