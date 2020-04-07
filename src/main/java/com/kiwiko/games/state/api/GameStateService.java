package com.kiwiko.games.state.api;

import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.data.GameType;

import java.util.Optional;

public interface GameStateService {

    Optional<GameState> findForGame(GameType gameType, long gameId);

    <T> Optional<T> reconstructGame(GameType gameType, long gameId, Class<T> gameStateClass);

    GameState saveGameState(GameState gameState);

    long getNewGameId(GameType gameType);
}
