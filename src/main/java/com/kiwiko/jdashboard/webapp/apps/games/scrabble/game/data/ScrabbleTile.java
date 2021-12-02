package com.kiwiko.jdashboard.webapp.apps.games.scrabble.game.data;

import com.kiwiko.library.persistence.identification.GeneratedLongIdentifiable;

import javax.annotation.Nullable;
import java.util.Objects;

public class ScrabbleTile extends GeneratedLongIdentifiable {

    private final String character;
    private final String playerId;

    // For ScrabbleSubmittedTile use only
    protected @Nullable Integer row;
    protected @Nullable Integer column;

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
        if (this == other) {
            return true;
        }

        if (other == null || !ScrabbleTile.class.isAssignableFrom(other.getClass())) {
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
