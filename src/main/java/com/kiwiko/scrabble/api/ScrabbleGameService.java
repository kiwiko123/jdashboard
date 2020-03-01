package com.kiwiko.scrabble.api;

import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.scrabble.errors.ScrabbleException;
import com.kiwiko.scrabble.game.ScrabbleGame;
import com.kiwiko.scrabble.game.ScrabbleGameBoard;
import com.kiwiko.scrabble.game.ScrabblePlayer;
import com.kiwiko.scrabble.game.ScrabbleSubmittedTile;
import com.kiwiko.scrabble.game.ScrabbleTile;
import com.kiwiko.scrabble.game.helpers.ScrabbleGameHelper;

import javax.inject.Inject;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

public class ScrabbleGameService {

    private static final int gameBoardDimensions = 10;
    private static final int numberOfAvailableTiles = 8;

    private final CacheService cacheService;
    private final ScrabbleGameHelper gameHelper;

    @Inject
    public ScrabbleGameService(
            CacheService cacheService,
            ScrabbleGameHelper gameHelper) {
        this.cacheService = cacheService;
        this.gameHelper = gameHelper;
    }

    public ScrabbleGame createGame() {
        ScrabbleGameBoard board = new ScrabbleGameBoard(gameBoardDimensions, gameBoardDimensions);
        ScrabblePlayer player = gameHelper.createPlayer("player", numberOfAvailableTiles);
        ScrabblePlayer opponent = gameHelper.createPlayer("opponent", numberOfAvailableTiles);

        ScrabbleGame game = new ScrabbleGame(
                board,
                player,
                opponent);

        String cacheKey = getGameCacheKey(game.getId());
        cacheService.cache(cacheKey, game, Duration.ofHours(1));
        return game;
    }

    public ScrabbleGame getGameById(long id) {
        String cacheKey = getGameCacheKey(id);
        return cacheService.get(cacheKey, ScrabbleGame.class)
                .orElseThrow(() -> new ScrabbleException(String.format("Failed to retrieve ScrabbleGame for game ID %d", id)));
    }

    public ScrabblePlayer getPlayerById(ScrabbleGame game, String playerId) {
        Optional<ScrabblePlayer> player = gameHelper.getPlayerById(game, playerId);

        if (!player.isPresent()) {
            throw new ScrabbleException(String.format("Player with id \"%s\" doesn't exist", playerId));
        }

        return player.get();
    }

    public boolean placeMove(ScrabbleGame game, ScrabblePlayer player, ScrabbleTile move) {
        // TODO
        return false;
    }

    public boolean areTilesValid(ScrabbleGame game, Collection<ScrabbleSubmittedTile> tiles) {
        return gameHelper.areTilesValid(game, tiles);
    }

    private String getGameCacheKey(long id) {
        return String.format("ScrabbleGame-%d", id);
    }
}
