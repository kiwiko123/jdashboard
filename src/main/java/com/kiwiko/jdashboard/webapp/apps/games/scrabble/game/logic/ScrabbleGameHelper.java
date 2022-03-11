package com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.logic;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.errors.ScrabbleException;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.data.ScrabbleGame;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.data.ScrabblePlayer;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.data.ScrabbleSubmittedTile;
import com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.data.ScrabbleTile;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScrabbleGameHelper {

    @Inject private ScrabbleMoveHelper scrabbleMoveHelper;
    @Inject private ScrabbleCreateGameHelper createGameHelper;
    @Inject private Logger logger;

    public Optional<ScrabblePlayer> getPlayerById(ScrabbleGame game, String playerId) {
        return Stream.of(game.getPlayer(), game.getOpponent())
                .filter(p -> Objects.equals(p.getId(), playerId))
                .findFirst();
    }

    public boolean validateMove(ScrabbleGame game, Collection<ScrabbleSubmittedTile> tiles) {
        Collection<ScrabbleSubmittedTile> invalidTiles = scrabbleMoveHelper.getInvalidTiles(game, tiles);
        if (!invalidTiles.isEmpty()) {
            return false;
        }

        String word = tiles.stream()
                .map(ScrabbleTile::getCharacter)
                .collect(Collectors.joining());
        return scrabbleMoveHelper.isValidWord(word);
    }

    public boolean placeTiles(ScrabbleGame game, Collection<ScrabbleSubmittedTile> tiles) {
        boolean isValidMove = validateMove(game, tiles);
        if (!isValidMove) {
            logger.warn(String.format("Invalid move %d: %s", game.getId(), tiles.toString()));
            return false;
        }

        for (ScrabbleSubmittedTile tile : tiles) {
            ScrabblePlayer player = getPlayerById(game, tile.getPlayerId())
                    .orElseThrow(() -> new ScrabbleException(String.format("Unknown player ID \"%s\" when submitting tile %s", tile.getPlayerId(), tile.getCharacter())));

            game.getBoard().set(tile);
            player.getAvailableTiles().remove(tile);
        }

        String submittingPlayerId = tiles.stream()
                .map(ScrabbleTile::getPlayerId)
                .findFirst()
                .orElseThrow(() -> new ScrabbleException("No player ID found"));
        ScrabblePlayer submittingPlayer = getPlayerById(game, submittingPlayerId)
                .orElseThrow(() -> new ScrabbleException("No player found"));
        Collection<ScrabbleTile> createdTiles = createGameHelper.createRandomTiles(submittingPlayerId, tiles.size());
        submittingPlayer.getAvailableTiles().addAll(createdTiles);

        return true;
    }
}
