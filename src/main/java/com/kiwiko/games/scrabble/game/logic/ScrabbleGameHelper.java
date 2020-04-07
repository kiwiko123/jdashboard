package com.kiwiko.games.scrabble.game.logic;

import com.kiwiko.games.scrabble.game.data.ScrabbleGame;
import com.kiwiko.games.scrabble.game.data.ScrabblePlayer;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ScrabbleGameHelper {

    public Optional<ScrabblePlayer> getPlayerById(ScrabbleGame game, String playerId) {
        return Stream.of(game.getPlayer(), game.getOpponent())
                .filter(p -> Objects.equals(p.getId(), playerId))
                .findFirst();
    }
}
