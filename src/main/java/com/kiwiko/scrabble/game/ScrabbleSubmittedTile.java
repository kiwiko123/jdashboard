package com.kiwiko.scrabble.game;

public class ScrabbleSubmittedTile extends ScrabbleTile {

    private final int row;
    private final int column;

    public ScrabbleSubmittedTile(
            long id,
            String character,
            String playerId,
            int row,
            int column) {
        super(id, character, playerId);
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
