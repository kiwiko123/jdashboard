package com.kiwiko.webapp.apps.games.scrabble.api;

import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabbleGame;

import java.util.Optional;

public interface ScrabbleGameService {

    Optional<ScrabbleGame> getGameById(long gameId);

    void saveGame(ScrabbleGame game);

    void saveGameForUser(long gameId, long userId);

    Optional<ScrabbleGame> findMostRecentGameForUser(long userId);
}
