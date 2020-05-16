package com.kiwiko.webapp.games.scrabble.game.data;

import com.kiwiko.library.persistence.identification.TypeIdentifiable;

import java.util.Collection;

public class ScrabblePlayer extends TypeIdentifiable<String> {

    private String id;
    private Collection<ScrabbleTile> availableTiles;
    private Collection<ScrabbleTile> playedTiles;
    private Collection<ScrabbleSubmittedTile> submittedTiles;

    public ScrabblePlayer(
            String id,
            Collection<ScrabbleTile> availableTiles,
            Collection<ScrabbleTile> placedTiles,
            Collection<ScrabbleSubmittedTile> submittedTiles) {
        this.id = id;
        this.availableTiles = availableTiles;
        this.playedTiles = placedTiles;
        this.submittedTiles = submittedTiles;
    }

    private ScrabblePlayer() { }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Collection<ScrabbleTile> getAvailableTiles() {
        return availableTiles;
    }

    public void setAvailableTiles(Collection<ScrabbleTile> availableTiles) {
        this.availableTiles = availableTiles;
    }

    public Collection<ScrabbleTile> getPlayedTiles() {
        return playedTiles;
    }

    public void setPlayedTiles(Collection<ScrabbleTile> playedTiles) {
        this.playedTiles = playedTiles;
    }

    public Collection<ScrabbleSubmittedTile> getSubmittedTiles() {
        return submittedTiles;
    }

    public void setSubmittedTiles(Collection<ScrabbleSubmittedTile> submittedTiles) {
        this.submittedTiles = submittedTiles;
    }
}
