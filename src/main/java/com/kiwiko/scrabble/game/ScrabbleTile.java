package com.kiwiko.scrabble.game;

import java.util.Objects;

public class ScrabbleTile {

    private final long id;
    private final String character;
    private final String playerId;

    public ScrabbleTile(long id, String character, String playerId) {
        this.id = id;
        this.character = character;
        this.playerId = playerId;
    }

    public long getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public String toString() {
        return String.format("ScrabbleTile(\"%d\", \"%s\", \"%s\")", id, character, playerId);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        ScrabbleTile otherTile = (ScrabbleTile) other;
        return id == otherTile.id
                && Objects.equals(character, otherTile.character)
                && Objects.equals(playerId, otherTile.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                character,
                playerId);
    }
}
