package com.kiwiko.webapp.apps.games.pazaak.api.interfaces.parameters;

import com.kiwiko.webapp.apps.games.pazaak.api.dto.PazaakCard;

import javax.annotation.Nullable;

public class PazaakEndTurnRequest {

    private Long gameId;
    private Long userId;
    private String playerId;
    private @Nullable PazaakCard playedCard;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Nullable
    public PazaakCard getPlayedCard() {
        return playedCard;
    }

    public PazaakEndTurnRequest setPlayedCard(@Nullable PazaakCard playedCard) {
        this.playedCard = playedCard;
        return this;
    }
}
