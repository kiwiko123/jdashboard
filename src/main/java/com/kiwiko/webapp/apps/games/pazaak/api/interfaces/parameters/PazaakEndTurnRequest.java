package com.kiwiko.webapp.apps.games.pazaak.api.interfaces.parameters;

import com.kiwiko.webapp.apps.games.pazaak.api.dto.PazaakCard;

import javax.annotation.Nullable;

public class PazaakEndTurnRequest extends PazaakLoadGameParameters {

    private String playerId;
    private @Nullable PazaakCard playedCard;

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
