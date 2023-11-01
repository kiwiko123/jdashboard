package com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto;

import javax.annotation.Nullable;

public class PazaakGame {

    private PazaakPlayer player;
    private PazaakPlayer opponent;
    private @Nullable String currentPlayerId;
    private @Nullable String winningPlayerId;
    private @Nullable Long gameId;

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

    @Nullable
    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(@Nullable String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    @Nullable
    public String getWinningPlayerId() {
        return winningPlayerId;
    }

    public void setWinningPlayerId(@Nullable String winningPlayerId) {
        this.winningPlayerId = winningPlayerId;
    }

    @Nullable
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(@Nullable Long gameId) {
        this.gameId = gameId;
    }
}
