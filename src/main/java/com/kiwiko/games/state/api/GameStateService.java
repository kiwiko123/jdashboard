package com.kiwiko.games.state.api;

import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.data.GameType;

import java.util.Collection;
import java.util.Optional;

public interface GameStateService {

    Optional<GameState> findForGame(GameType gameType, long gameId);

    <T> Optional<T> reconstructGame(GameType gameType, long gameId, Class<T> gameStateClass);

    GameState saveGameState(GameState gameState);

    GameState saveForUser(GameState gameState, long userId);

    Optional<GameState> findByGameStateAndUser(long gameStateId, long userId);

    long getNewGameId(GameType gameType);

    Optional<GameState> getById(long gameStateId);

    Collection<GameState> findGamesForUser(long userId, GameType gameType);
}
