package com.kiwiko.webapp.games.scrabble.game.data;

import com.kiwiko.webapp.games.scrabble.errors.ScrabbleException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScrabbleGameBoard {

    private List<List<ScrabbleTile>> board;
    private int rowCount;
    private int columnCount;
    private int tileCount;

    public ScrabbleGameBoard(int rows, int columns) {
        this.rowCount = rows;
        this.columnCount = columns;
        List<ScrabbleTile> row = new ArrayList<>(Collections.nCopies(columns, null));
        board = new ArrayList<>(Collections.nCopies(rows, row));
        tileCount = 0;
    }

    private ScrabbleGameBoard(ScrabbleGameBoard other) {
        board = other.board.stream()
                .map(ArrayList::new)
                .collect(Collectors.toList());
        rowCount = other.rowCount;
        columnCount = other.columnCount;
        tileCount = other.tileCount;
    }

    private ScrabbleGameBoard() { }

    public void setBoard(List<List<ScrabbleTile>> board) {
        this.board = board;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getTileCount() {
        return tileCount;
    }

    public void setTileCount(int tileCount) {
        this.tileCount = tileCount;
    }

    public boolean isEmpty() {
        return tileCount == 0;
    }

    public Optional<ScrabbleTile> get(int row, int column) throws IndexOutOfBoundsException {
        if (!isValidCoordinate(row, column)) {
            throw new IndexOutOfBoundsException(String.format("Verify coordinates (%d, %d)", row, column));
        }

        ScrabbleTile result = board.get(row).get(column);
        return Optional.ofNullable(result);
    }

    public void set(int row, int column, ScrabbleTile tile) {
        if (get(row, column).isPresent()) {
            throw new ScrabbleException(String.format("Position (%d, %d) is taken", row, column));
        }
        board.get(row).set(column, tile);
        ++tileCount;
    }

    public void set(ScrabbleSubmittedTile tile) {
        set(tile.getRow(), tile.getColumn(), tile);
    }

    public boolean isValidCoordinate(int row, int column) {
        return row >= 0 && row < getRowCount() && column >= 0 && column < getColumnCount();
    }

    /**
     * Returns a copy of the current board, with the given submitted tiles applied to it.
     * Does not modify {@code this}.
     *
     * @param tiles the tiles to apply to the new board.
     * @return a copy of the current board, with the given tiles applied to it.
     */
    public ScrabbleGameBoard applyView(Collection<ScrabbleSubmittedTile> tiles) {
        ScrabbleGameBoard copy = new ScrabbleGameBoard(this);
        tiles.stream()
                .forEach(tile -> copy.set(tile.getRow(), tile.getColumn(), tile));
        return copy;
    }

    public void print() {
        for (List<ScrabbleTile> row : board) {
            row.stream()
                    .forEach(move -> System.out.print(String.format("%s ", move.toString())));
            System.out.println();
        }
    }
}
