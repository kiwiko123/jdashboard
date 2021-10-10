package com.kiwiko.webapp.apps.games.scrabble.game.logic;

import com.google.common.collect.HashMultiset;
import com.kiwiko.webapp.apps.games.scrabble.api.ScrabbleGameService;
import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabbleGame;
import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabbleGameBoard;
import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabblePlayer;
import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabbleTile;
import com.kiwiko.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.webapp.apps.games.state.data.GameType;
import com.kiwiko.webapp.users.data.User;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ScrabbleCreateGameHelper {

    private static final Random random = new Random();
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int gameBoardDimensions = 10;
    private static final int numberOfAvailableTiles = 8;

    @Inject
    private ScrabbleGameService scrabbleGameService;

    @Inject
    private GameStateService gameStateService;

    public ScrabbleGame getOrCreateGame(@Nullable User user, @Nullable Long gameId) {
        if (user == null) {
            return createGame();
        }

        // TODO cross-check game ID with user

        ScrabbleGame mostRecentGame = scrabbleGameService.findMostRecentGameForUser(user.getId())
                .orElse(null);

        if (mostRecentGame != null) {
            return mostRecentGame;
        }

        ScrabbleGame game = createGame();
        scrabbleGameService.saveGameForUser(game.getId(), user.getId());
        return game;
    }

    public ScrabbleGame createGame() {
        long gameId = gameStateService.getNewGameId(GameType.SCRABBLE.getName());
        ScrabbleGameBoard board = new ScrabbleGameBoard(gameBoardDimensions, gameBoardDimensions);
        ScrabblePlayer player = createPlayer("player", numberOfAvailableTiles);
        ScrabblePlayer opponent = createPlayer("opponent", numberOfAvailableTiles);

        ScrabbleGame game = new ScrabbleGame(
                gameId,
                board,
                player,
                opponent);

        scrabbleGameService.saveGame(game);
        return game;
    }

    public Collection<ScrabbleTile> createRandomTiles(String playerId, int numberOfTiles) {
        return makeRandomCharacters(numberOfTiles).stream()
                .map(character -> new ScrabbleTile(character, playerId))
                .collect(Collectors.collectingAndThen(Collectors.toList(), HashMultiset::create));
    }

    public ScrabblePlayer createPlayer(String playerId, int numberOfTiles) {
        Collection<ScrabbleTile> availableTiles = createRandomTiles(playerId, numberOfTiles);
        return new ScrabblePlayer(playerId, availableTiles, HashMultiset.create(), HashMultiset.create());
    }

    public List<String> makeRandomCharacters(int size) {
        List<String> randomCharacters = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            int index = random.nextInt(alphabet.length());
            String character = Character.toString(alphabet.charAt(index));
            randomCharacters.add(character);
        }

        return randomCharacters;
    }
}
