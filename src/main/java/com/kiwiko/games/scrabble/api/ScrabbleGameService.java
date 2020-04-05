package com.kiwiko.games.scrabble.api;

import com.kiwiko.games.scrabble.game.ScrabbleGame;
import com.kiwiko.games.scrabble.game.ScrabblePlayer;
import com.kiwiko.games.scrabble.game.ScrabbleSubmittedTile;
import com.kiwiko.games.scrabble.game.ScrabbleTile;

import java.util.Collection;

public interface ScrabbleGameService {

    ScrabbleGame createGame();

    ScrabbleGame getGameById(long id);

    ScrabblePlayer getPlayerById(ScrabbleGame game, String playerId);

    boolean placeMove(ScrabbleGame game, ScrabblePlayer player, ScrabbleTile move);

    Collection<ScrabbleSubmittedTile> getInvalidTiles(ScrabbleGame game, Collection<ScrabbleSubmittedTile> tiles);
}
