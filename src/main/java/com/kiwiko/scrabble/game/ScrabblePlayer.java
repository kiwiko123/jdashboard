package com.kiwiko.scrabble.game;

import com.kiwiko.games.api.GamePlayer;

import java.util.Collection;

public class ScrabblePlayer extends GamePlayer {

    private final Collection<ScrabbleTile> availableTiles;
    private final Collection<ScrabbleTile> playedTiles;
    private final Collection<ScrabbleSubmittedTile> submittedTiles;

    public ScrabblePlayer(
            String id,
            Collection<ScrabbleTile> availableTiles,
            Collection<ScrabbleTile> placedTiles,
            Collection<ScrabbleSubmittedTile> submittedTiles) {
        super(id);
        this.availableTiles = availableTiles;
        this.playedTiles = placedTiles;
        this.submittedTiles = submittedTiles;
    }

    public Collection<ScrabbleTile> getAvailableTiles() {
        return availableTiles;
    }

    public Collection<ScrabbleTile> getPlayedTiles() {
        return playedTiles;
    }

    public Collection<ScrabbleSubmittedTile> getSubmittedTiles() {
        return submittedTiles;
    }
}
