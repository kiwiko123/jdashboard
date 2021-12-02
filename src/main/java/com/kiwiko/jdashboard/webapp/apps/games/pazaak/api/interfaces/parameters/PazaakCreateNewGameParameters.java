package com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters;

public class PazaakCreateNewGameParameters {

    private Long playerUserId;
    private String playerId;
    private String opponentId;

    public Long getPlayerUserId() {
        return playerUserId;
    }

    public void setPlayerUserId(Long playerUserId) {
        this.playerUserId = playerUserId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }
}
