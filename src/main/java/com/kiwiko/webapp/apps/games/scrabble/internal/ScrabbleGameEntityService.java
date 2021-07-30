package com.kiwiko.webapp.apps.games.scrabble.internal;

import com.kiwiko.webapp.apps.games.scrabble.api.ScrabbleGameService;
import com.kiwiko.webapp.apps.games.scrabble.errors.ScrabbleException;
import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabbleGame;
import com.kiwiko.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.webapp.apps.games.state.data.GameState;
import com.kiwiko.webapp.apps.games.state.data.GameType;
import com.kiwiko.webapp.mvc.json.api.JsonMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Comparator;
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
        GameState gameState = getGameStateFromGame(game);
        gameStateService.saveGameState(gameState);
    }

    @Transactional
    @Override
    public void saveGameForUser(long gameId, long userId) {
        ScrabbleGame game = getGameById(gameId)
                .orElseThrow(() -> new ScrabbleException(String.format("No game found with ID %d", gameId)));
        GameState gameState = getGameStateFromGame(game);
        gameStateService.saveForUser(gameState, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ScrabbleGame> findMostRecentGameForUser(long userId) {
        return gameStateService.findGamesForUser(userId, GameType.SCRABBLE).stream()
                .max(Comparator.comparing(GameState::getCreatedDate))
                .map(GameState::getGameId)
                .flatMap(this::getGameById);
    }

    private GameState createNewScrabbleGameState(ScrabbleGame game) {
        GameState gameState = new GameState();
        gameState.setGameType(GameType.SCRABBLE.getName());
        gameState.setGameId(game.getId());

        return gameState;
    }

    private GameState getGameStateFromGame(ScrabbleGame game) {
        String gameJson = jsonMapper.writeValueAsString(game);

        GameState gameState = gameStateService.findForGame(GameType.SCRABBLE, game.getId())
                .orElseGet(() -> createNewScrabbleGameState(game));
        gameState.setGameStateJson(gameJson);

        return gameState;
    }
}
