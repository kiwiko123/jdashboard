package com.kiwiko.games.api;

import com.kiwiko.persistence.identification.TypeIdentifiable;

public class GamePlayer extends TypeIdentifiable<String> {

    public GamePlayer(String id) {
        super(id);
    }
}
