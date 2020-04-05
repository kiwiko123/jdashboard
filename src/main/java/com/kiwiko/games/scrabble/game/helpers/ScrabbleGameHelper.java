package com.kiwiko.games.scrabble.game.helpers;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.kiwiko.dataStructures.Pair;
import com.kiwiko.games.scrabble.game.ScrabbleGame;
import com.kiwiko.games.scrabble.game.ScrabbleGameBoard;
import com.kiwiko.games.scrabble.game.ScrabblePlayer;
import com.kiwiko.games.scrabble.game.ScrabbleSubmittedTile;
import com.kiwiko.games.scrabble.game.ScrabbleTile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScrabbleGameHelper {

    private static final Random random = new Random();
    private static final Set<Pair<Integer, Integer>> coordinateDirections = ImmutableSet.of(
            new Pair<>(0, -1),  // up
            new Pair<>(1, 0),   // right
            new Pair<>(1, 1),   // down
            new Pair<>(-1, 0)); // left

    public ScrabblePlayer createPlayer(String playerId, int numberOfTiles) {
        Collection<ScrabbleTile> availableTiles = makeRandomCharacters(numberOfTiles).stream()
                .map(character -> new ScrabbleTile(character, playerId))
                .collect(Collectors.collectingAndThen(Collectors.toList(), HashMultiset::create));

        return new ScrabblePlayer(playerId, availableTiles, HashMultiset.create(), HashMultiset.create());
    }

    public Optional<ScrabblePlayer> getPlayerById(ScrabbleGame game, String playerId) {
        return Stream.of(game.getPlayer(), game.getOpponent())
                .filter(p -> Objects.equals(p.getId(), playerId))
                .findFirst();
    }

    public boolean placeMove(
            ScrabbleGame game,
            String playerId,
            ScrabbleTile move) {
        return false;
    }

    public Collection<ScrabbleSubmittedTile> getInvalidTiles(ScrabbleGame game, Collection<ScrabbleSubmittedTile> submittedTiles) {
        ScrabbleGameBoard board = game.getBoard();

        // Make sure that none of the submitted tiles are already placed.
        Collection<ScrabbleSubmittedTile> overlappingTiles = submittedTiles.stream()
                .filter(tile -> board.get(tile.getRow(), tile.getColumn()).isPresent())
                .collect(Collectors.toSet());
        if (!overlappingTiles.isEmpty()) {
            return overlappingTiles;
        }

        // If the entire board is empty (i.e., the very first move),
        // then we don't need to validate the _very first_ tile that's placed.
        if (board.isEmpty() && submittedTiles.size() == 1) {
            return new HashSet<>();
        }

        ScrabbleGameBoard view = board.applyView(submittedTiles);
        return submittedTiles.stream()
                .filter(tile -> !hasAdjacentTile(view, tile.getRow(), tile.getColumn()))
                .collect(Collectors.toSet());
    }

    public boolean hasAdjacentTile(ScrabbleGameBoard board, int row, int column) {
        return coordinateDirections.stream()
                .anyMatch(coordinate -> hasDirectionallyAdjacentTile(board, row, column, coordinate.getFirst(), coordinate.getSecond()));
    }

    private boolean hasDirectionallyAdjacentTile(ScrabbleGameBoard board, int row, int column, int directionX, int directionY) {
        int newRow = row + directionY;
        int newColumn = column + directionX;

        return board.isValidCoordinate(newRow, newColumn) && board.get(newRow, newColumn).isPresent();
    }

    private List<String> makeRandomCharacters(int size) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<String> randomCharacters = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            int index = random.nextInt(alphabet.length());
            String character = Character.toString(alphabet.charAt(index));
            randomCharacters.add(character);
        }

        return randomCharacters;
    }
}
