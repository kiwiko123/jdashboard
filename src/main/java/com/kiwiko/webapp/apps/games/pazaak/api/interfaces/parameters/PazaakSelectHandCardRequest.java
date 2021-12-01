package com.kiwiko.webapp.apps.games.pazaak.api.interfaces.parameters;

import com.kiwiko.webapp.apps.games.pazaak.api.dto.PazaakCard;

public class PazaakSelectHandCardRequest extends PazaakLoadGameParameters {

    private String playerId;
    private PazaakCard selectedHandCard;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public PazaakCard getSelectedHandCard() {
        return selectedHandCard;
    }

    public void setSelectedHandCard(PazaakCard selectedHandCard) {
        this.selectedHandCard = selectedHandCard;
    }
}
