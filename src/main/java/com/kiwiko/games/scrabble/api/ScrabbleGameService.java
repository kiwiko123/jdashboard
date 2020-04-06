package com.kiwiko.games.scrabble.api;

import com.kiwiko.games.scrabble.game.data.ScrabbleGame;

import java.util.Optional;

public interface ScrabbleGameService {

    Optional<ScrabbleGame> getGameById(long gameId);

    void saveGame(ScrabbleGame game);
}
