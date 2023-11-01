package com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces;

import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakGame;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakLoadGameParameters;
import com.kiwiko.jdashboard.webapp.apps.games.state.api.GameStateService;

import javax.inject.Inject;
import java.util.Objects;

public class PazaakGameLoader {

    @Inject private GameStateService gameStateService;

    public PazaakGame loadGame(PazaakLoadGameParameters parameters) {
        Objects.requireNonNull(parameters.getGameId(), "Game ID is required to load game");
        Objects.requireNonNull(parameters.getUserId(), "User ID is required to load game");

        return gameStateService.reconstructGame(PazaakGameProperties.GAME_TYPE_ID, parameters.getGameId(), PazaakGame.class)
                .orElseThrow(() -> new PazaakGameException(String.format("No game found with ID %d", parameters.getGameId())));
    }
}
