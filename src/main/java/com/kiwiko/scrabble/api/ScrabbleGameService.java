package com.kiwiko.scrabble.api;

import com.kiwiko.scrabble.errors.ScrabbleException;
import com.kiwiko.scrabble.game.ScrabbleGame;
import com.kiwiko.scrabble.game.ScrabbleGameBoard;
import com.kiwiko.scrabble.game.ScrabblePlayer;
import com.kiwiko.scrabble.game.ScrabbleTile;
import com.kiwiko.scrabble.game.helpers.ScrabbleGameHelper;

import javax.inject.Inject;
import java.util.Optional;

public class ScrabbleGameService {

    private static final int gameBoardDimensions = 10;
    private static final int numberOfAvailableTiles = 8;

    private final ScrabbleGameHelper gameHelper;

    @Inject
    public ScrabbleGameService(ScrabbleGameHelper gameHelper) {
        this.gameHelper = gameHelper;
    }

    public ScrabbleGame createGame() {
        ScrabbleGameBoard board = new ScrabbleGameBoard(gameBoardDimensions, gameBoardDimensions);
        ScrabblePlayer player = gameHelper.createPlayer("player", numberOfAvailableTiles);
        ScrabblePlayer opponent = gameHelper.createPlayer("opponent", numberOfAvailableTiles);

        return new ScrabbleGame(board, player, opponent);
    }

    public ScrabblePlayer getPlayerById(ScrabbleGame game, String playerId) {
        Optional<ScrabblePlayer> player = gameHelper.getPlayerById(game, playerId);

        if (!player.isPresent()) {
            throw new ScrabbleException(String.format("Player with id \"%s\" doesn't exist", playerId));
        }

        return player.get();
    }

    public boolean placeMove(ScrabbleGame game, ScrabblePlayer player, ScrabbleTile move) {

        return false;
    }
}
