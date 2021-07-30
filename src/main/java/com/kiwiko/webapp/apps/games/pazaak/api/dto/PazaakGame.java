package com.kiwiko.webapp.apps.games.pazaak.api.dto;

public class PazaakGame {

    private PazaakPlayer player;
    private PazaakPlayer opponent;

    public PazaakPlayer getPlayer() {
        return player;
    }

    public void setPlayer(PazaakPlayer player) {
        this.player = player;
    }

    public PazaakPlayer getOpponent() {
        return opponent;
    }

    public void setOpponent(PazaakPlayer opponent) {
        this.opponent = opponent;
    }
}
