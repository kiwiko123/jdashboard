package com.kiwiko.games.api;

import com.kiwiko.persistence.Identifiable;

public class GamePlayer extends Identifiable<String> {

    public GamePlayer(String id) {
        super(id);
    }
}
