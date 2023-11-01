package com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters;

public class PazaakLoadGameParameters {

    private Long gameId;
    private Long userId;

    public Long getGameId() {
        return gameId;
    }

    public PazaakLoadGameParameters setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public PazaakLoadGameParameters setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
