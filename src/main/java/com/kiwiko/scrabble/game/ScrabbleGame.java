package com.kiwiko.scrabble.game;

import com.kiwiko.persistence.LongIdentifiable;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class ScrabbleGame extends LongIdentifiable {

    private final ScrabbleGameBoard board;
    private final ScrabblePlayer player;
    private final ScrabblePlayer opponent;
    private @Nullable String error;

    public ScrabbleGame(
            ScrabbleGameBoard board,
            ScrabblePlayer player,
            ScrabblePlayer opponent) {
        super();
        this.board = board;
        this.player = player;
        this.opponent = opponent;
    }

    public ScrabbleGameBoard getBoard() {
        return board;
    }

    public ScrabblePlayer getPlayer() {
        return player;
    }

    public ScrabblePlayer getOpponent() {
        return opponent;
    }

    public Optional<String> getError() {
        return Optional.ofNullable(error);
    }

    public void setError(@Nullable String error) {
        this.error = error;
    }

    public boolean isGameOver() {
        // TODO
        return false;
    }
}
