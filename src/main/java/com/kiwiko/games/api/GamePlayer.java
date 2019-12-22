package com.kiwiko.games.api;

import java.util.Objects;

public class GamePlayer {

    private final String id;

    public GamePlayer(String id) {
        this.id = id;
    }

    public final String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Player(\"%s\")", getId());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof GamePlayer)) {
            return false;
        }

        GamePlayer otherPlayer = (GamePlayer) other;
        return Objects.equals(id, otherPlayer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
