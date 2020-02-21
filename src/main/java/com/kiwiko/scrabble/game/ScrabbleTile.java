package com.kiwiko.scrabble.game;

import com.kiwiko.persistence.LongIdentifiable;

import java.util.Objects;

public class ScrabbleTile extends LongIdentifiable {

    private final String character;
    private final String playerId;

    public ScrabbleTile(String character, String playerId) {
        super();
        this.character = character;
        this.playerId = playerId;
    }

    public String getCharacter() {
        return character;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public String toString() {
        return String.format("ScrabbleTile(\"%d\", \"%s\", \"%s\")", getId(), character, playerId);
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }

        ScrabbleTile otherTile = (ScrabbleTile) other;
        return Objects.equals(character, otherTile.character)
                && Objects.equals(playerId, otherTile.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(),
                character,
                playerId);
    }
}
