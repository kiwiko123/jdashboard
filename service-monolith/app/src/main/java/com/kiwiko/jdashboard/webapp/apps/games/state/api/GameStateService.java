package com.kiwiko.jdashboard.webapp.apps.games.state.api;

import com.kiwiko.jdashboard.webapp.apps.games.state.api.parameters.FindGameStateParameters;
import com.kiwiko.jdashboard.webapp.apps.games.state.data.GameState;
import com.kiwiko.jdashboard.webapp.apps.games.state.data.GameType;

import java.util.Collection;
import java.util.Optional;

public interface GameStateService {

    Optional<GameState> findForGame(String gameType, long gameId);

    <T> Optional<T> reconstructGame(String gameType, long gameId, Class<T> gameStateClass);

    GameState saveGameState(GameState gameState);

    GameState saveForUser(GameState gameState, long userId);

    Optional<GameState> findByGameStateAndUser(long gameStateId, long userId);

    long getNewGameId(String gameType);

    Optional<GameState> getById(long gameStateId);

    Collection<GameState> findGamesForUser(long userId, GameType gameType);

    Optional<GameState> findGameState(FindGameStateParameters parameters);

    Optional<GameState> get(long id);
    GameState create(GameState gameState);
    GameState update(GameState gameState);
    GameState merge(GameState gameState);
}
