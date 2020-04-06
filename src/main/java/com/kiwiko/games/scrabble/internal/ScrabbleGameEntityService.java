package com.kiwiko.games.scrabble.internal;

import com.kiwiko.games.scrabble.api.ScrabbleGameService;
import com.kiwiko.games.scrabble.game.data.ScrabbleGame;
import com.kiwiko.games.scrabble.game.logic.ScrabbleCreateGameHelper;
import com.kiwiko.games.state.api.GameStateService;
import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.data.GameType;
import com.kiwiko.memory.caching.api.CacheService;
import com.kiwiko.metrics.api.LogService;
import com.kiwiko.mvc.json.api.PropertyObjectMapper;
import com.kiwiko.mvc.json.api.errors.JsonException;

import javax.inject.Inject;
import java.util.Optional;

public class ScrabbleGameEntityService implements ScrabbleGameService {

    private static final int gameBoardDimensions = 10;
    private static final int numberOfAvailableTiles = 8;

    @Inject
    private CacheService cacheService;

    @Inject
    private ScrabbleCreateGameHelper gameHelper;

    @Inject
    private GameStateService gameStateService;

    @Inject
    private PropertyObjectMapper propertyObjectMapper;

    @Inject
    private LogService logService;

    public Optional<ScrabbleGame> getGameById(long gameId) {
        GameState gameState = gameStateService.findForGame(GameType.SCRABBLE, gameId)
                .orElse(null);
        if (gameState == null) {
            logService.warn(String.format("No Scrabble game found with ID %d", gameId));
            return Optional.empty();
        }

        String gameStateJson = gameState.getGameStateJson()
                .orElse(null);
        if (gameStateJson == null) {
            logService.warn(String.format("No Scrabble game state found for game ID %d", gameId));
            return Optional.empty();
        }

        ScrabbleGame game = null;
        try {
            game = propertyObjectMapper.readValue(gameStateJson, ScrabbleGame.class);
        } catch (JsonException e) {
            logService.error(String.format("Error converting game state into ScrabbleGame for game ID %d", gameId), e);
        }

        return Optional.ofNullable(game);
    }

    public void saveGame(ScrabbleGame game) {
        String gameJson = propertyObjectMapper.writeValueAsString(game);

        GameState gameState = new GameState();
        gameState.setGameType(GameType.SCRABBLE);
        gameState.setGameId(game.getId());
        gameState.setGameStateJson(gameJson);

        gameStateService.saveGameState(gameState);
    }
}
