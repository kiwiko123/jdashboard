package com.kiwiko.games.state.data;

import java.util.Arrays;
import java.util.Optional;

public enum GameType {
    SCRABBLE("Scrabble");

    private final String name;

    GameType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<GameType> getById(String id) {
        return Arrays.stream(values())
                .filter(gameType -> gameType.toString().equalsIgnoreCase(id))
                .findFirst();
    }
}
