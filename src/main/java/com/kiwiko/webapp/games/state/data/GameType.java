package com.kiwiko.webapp.games.state.data;

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

    public static Optional<GameType> getByName(String name) {
        return Arrays.stream(values())
                .filter(gameType -> gameType.toString().equalsIgnoreCase(name) || gameType.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
