package com.kiwiko.scrabble.game;

import com.kiwiko.scrabble.ScrabbleConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ScrabbleGameBoard {

    private final List<List<ScrabbleTile>> board;
    private final int rowCount;
    private final int columnCount;

    public ScrabbleGameBoard(int rows, int columns) {
        this.rowCount = rows;
        this.columnCount = columns;
        List<ScrabbleTile> row = new ArrayList<ScrabbleTile>(Collections.nCopies(columns, null));
        board = new ArrayList<>(Collections.nCopies(rows, row));
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    /**
     * For serialization purposes only.
     * This method is protected to remain visible by Jackson,
     * but should not be used for accessing the board.
     * Use {@link #get(int, int)} instead.
     *
     * @see ScrabbleConfiguration#jackson2ObjectMapperBuilder()
     */
    protected List<List<ScrabbleTile>> getBoard() {
        return board;
    }

    public Optional<ScrabbleTile> get(int row, int column) throws IndexOutOfBoundsException {
        if (!isValidCoordinate(row, column)) {
            throw new IndexOutOfBoundsException(String.format("Verify coordinates (%d, %d)", row, column));
        }

        ScrabbleTile result = board.get(row).get(column);
        return Optional.ofNullable(result);
    }

    public boolean isValidCoordinate(int row, int column) {
        return row >= 0 && row < getRowCount() && column >= 0 && column < getColumnCount();
    }

    public void print() {
        for (List<ScrabbleTile> row : board) {
            row.stream()
                    .forEach(move -> System.out.print(String.format("%s ", move.toString())));
            System.out.println();
        }
    }
}
