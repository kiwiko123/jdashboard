package com.kiwiko.webapp.apps.games.scrabble.game.logic;

import com.google.common.collect.ImmutableSet;
import com.kiwiko.library.dataStructures.Pair;
import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabbleGame;
import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabbleGameBoard;
import com.kiwiko.webapp.apps.games.scrabble.game.data.ScrabbleSubmittedTile;
import com.kiwiko.webapp.apps.games.scrabble.words.api.WordService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ScrabbleMoveHelper {

    private static final Set<Pair<Integer, Integer>> COORDINATE_DIRECTIONS = ImmutableSet.of(
            new Pair<>(0, -1),  // up
            new Pair<>(1, 0),   // right
            new Pair<>(1, 1),   // down
            new Pair<>(-1, 0)); // left

    @Inject
    private WordService wordService;

    public boolean isValidWord(String word) {
        return wordService.findByWord(word).isPresent();
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
        return COORDINATE_DIRECTIONS.stream()
                .anyMatch(coordinate -> hasDirectionallyAdjacentTile(board, row, column, coordinate.getFirst(), coordinate.getSecond()));
    }

    private boolean hasDirectionallyAdjacentTile(ScrabbleGameBoard board, int row, int column, int directionX, int directionY) {
        int newRow = row + directionY;
        int newColumn = column + directionX;

        return board.isValidCoordinate(newRow, newColumn) && board.get(newRow, newColumn).isPresent();
    }
}
