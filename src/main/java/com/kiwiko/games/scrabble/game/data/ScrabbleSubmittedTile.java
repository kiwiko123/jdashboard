package com.kiwiko.games.scrabble.game.data;

public class ScrabbleSubmittedTile extends ScrabbleTile {

    public ScrabbleSubmittedTile(
            String character,
            String playerId,
            int row,
            int column) {
        super(character, playerId);
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    // Don't override equals() or hashcode(),
    // because a ScrabbleSubmittedTile should be considered equal to a ScrabbleTile with the same character.
}
