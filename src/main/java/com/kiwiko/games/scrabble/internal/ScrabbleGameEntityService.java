package com.kiwiko.games.scrabble.internal;

import com.kiwiko.games.scrabble.api.ScrabbleGameService;
import com.kiwiko.games.scrabble.errors.ScrabbleException;
import com.kiwiko.games.scrabble.game.ScrabbleGame;
import com.kiwiko.games.scrabble.game.ScrabbleGameBoard;
import com.kiwiko.games.scrabble.game.ScrabblePlayer;
import com.kiwiko.games.scrabble.game.ScrabbleSubmittedTile;
import com.kiwiko.games.scrabble.game.ScrabbleTile;
import com.kiwiko.games.scrabble.game.helpers.ScrabbleGameHelper;
import com.kiwiko.games.state.api.GameStateService;
import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.data.GameType;
import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.mvc.json.api.PropertyObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

public class ScrabbleGameEntityService implements ScrabbleGameService {

    private static final int gameBoardDimensions = 10;
    private static final int numberOfAvailableTiles = 8;

    @Inject
    private CacheService cacheService;

    @Inject
    private ScrabbleGameHelper gameHelper;

    @Inject
    private GameStateService gameStateService;

    @Inject
    private PropertyObjectMapper propertyObjectMapper;

    public ScrabbleGame createGame() {
        ScrabbleGameBoard board = new ScrabbleGameBoard(gameBoardDimensions, gameBoardDimensions);
        ScrabblePlayer player = gameHelper.createPlayer("player", numberOfAvailableTiles);
        ScrabblePlayer opponent = gameHelper.createPlayer("opponent", numberOfAvailableTiles);

        ScrabbleGame game = new ScrabbleGame(
                board,
                player,
                opponent);

        saveGame(game);

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

    public Collection<ScrabbleSubmittedTile> getInvalidTiles(ScrabbleGame game, Collection<ScrabbleSubmittedTile> tiles) {
        return gameHelper.getInvalidTiles(game, tiles);
    }

    private String getGameCacheKey(long id) {
        return String.format("ScrabbleGame-%d", id);
    }

    private void saveGame(ScrabbleGame game) {
        String gameJson = propertyObjectMapper.writeValueAsString(game);

        GameState gameState = new GameState();
        gameState.setGameType(GameType.SCRABBLE);
        gameState.setGameId(game.getId());
        gameState.setGameStateJson(gameJson);

        gameStateService.saveGameState(gameState);
    }
}
