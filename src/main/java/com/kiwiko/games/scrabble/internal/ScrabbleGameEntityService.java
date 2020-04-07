package com.kiwiko.games.scrabble.internal;

import com.kiwiko.games.scrabble.api.ScrabbleGameService;
import com.kiwiko.games.scrabble.game.data.ScrabbleGame;
import com.kiwiko.games.state.api.GameStateService;
import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.data.GameType;
import com.kiwiko.mvc.json.api.JsonMapper;

import javax.inject.Inject;
import java.util.Optional;

public class ScrabbleGameEntityService implements ScrabbleGameService {

    @Inject
    private GameStateService gameStateService;

    @Inject
    private JsonMapper jsonMapper;

    public Optional<ScrabbleGame> getGameById(long gameId) {
        return gameStateService.reconstructGame(GameType.SCRABBLE, gameId, ScrabbleGame.class);
    }

    public void saveGame(ScrabbleGame game) {
        String gameJson = jsonMapper.writeValueAsString(game);

        GameState gameState = new GameState();
        gameState.setGameType(GameType.SCRABBLE);
        gameState.setGameId(game.getId());
        gameState.setGameStateJson(gameJson);

        gameStateService.saveGameState(gameState);
    }
}
