package com.kiwiko.games.state.internal;

import com.kiwiko.games.state.api.GameStateService;
import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.data.GameType;
import com.kiwiko.games.state.internal.dataAccess.GameStateEntity;
import com.kiwiko.games.state.internal.dataAccess.GameStateEntityDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

public class GameStateEntityService implements GameStateService {

    @Inject
    private GameStateEntityDAO gameStateEntityDAO;

    @Inject
    private GameStateEntityPropertyMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<GameState> findForGame(GameType gameType, long gameId) {
        return gameStateEntityDAO.findForGame(gameType, gameId)
                .map(mapper::toTargetType);
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
