package com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters;

import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakGame;

import javax.annotation.Nullable;

public class PazaakEndTurnResponse {

    private @Nullable PazaakGame game;
    private @Nullable String errorMessage;

    @Nullable
    public PazaakGame getGame() {
        return game;
    }

    public PazaakEndTurnResponse setGame(@Nullable PazaakGame game) {
        this.game = game;
        return this;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public PazaakEndTurnResponse setErrorMessage(@Nullable String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
