package com.kiwiko.games.scrabble.game.data;

import java.util.Objects;

public class ScrabbleSubmittedTile extends ScrabbleTile {

    private final int row;
    private final int column;

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

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }

        ScrabbleSubmittedTile otherTile = (ScrabbleSubmittedTile) other;
        return row == otherTile.row && column == otherTile.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(),
                getCharacter(),
                getPlayerId(),
                row,
                column);
    }
}
