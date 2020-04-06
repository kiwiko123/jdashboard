package com.kiwiko.games.scrabble.game.data;

import com.kiwiko.persistence.identification.Identifiable;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class ScrabbleGame implements Identifiable<Long> {

    private Long id;
    private ScrabbleGameBoard board;
    private ScrabblePlayer player;
    private ScrabblePlayer opponent;
    private @Nullable String error;

    public ScrabbleGame(
            Long id,
            ScrabbleGameBoard board,
            ScrabblePlayer player,
            ScrabblePlayer opponent) {
        this.id = id;
        this.board = board;
        this.player = player;
        this.opponent = opponent;
    }

    private ScrabbleGame() { }

    @Override
    public Long getId() {
        return id;
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
}
