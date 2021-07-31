package com.kiwiko.webapp.apps.games.pazaak.api.interfaces;

import com.kiwiko.webapp.apps.games.pazaak.api.dto.PazaakGame;
import com.kiwiko.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakLoadGameParameters;
import com.kiwiko.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.webapp.apps.games.state.api.parameters.FindGameStateParameters;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class PazaakGameLoader {

    @Inject private GameStateService gameStateService;

    public Optional<PazaakGame> loadGame(PazaakLoadGameParameters parameters) {
        Objects.requireNonNull(parameters.getGameId(), "Game ID is required to load");

        FindGameStateParameters findGameStateParameters = new FindGameStateParameters()
                .setGameType(PazaakGameProperties.GAME_TYPE_ID)
                .setGameId(parameters.getGameId())
                .setUserId(parameters.getUserId());

        // TODO only fetch once
        boolean gameExists = gameStateService.findGameState(findGameStateParameters).isPresent();
        if (!gameExists) {
            return Optional.empty();
        }

        return gameStateService.reconstructGame(PazaakGameProperties.GAME_TYPE_ID, parameters.getGameId(), PazaakGame.class);
    }
}
