package com.kiwiko.webapp.games.state.web;

import com.kiwiko.webapp.games.state.api.GameStateService;
import com.kiwiko.webapp.games.state.data.GameState;
import com.kiwiko.webapp.games.state.data.GameType;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL)
@RestController
public class GameStateAPIController {

    @Inject
    private GameStateService gameStateService;

    @GetMapping("/games/state/api/get/{gameType}/{gameId}")
    public ResponsePayload getGameStateForGame(
            @PathVariable(name = "gameType") String gameTypeId,
            @PathVariable(name = "gameId") long gameId) {
        GameType gameType = GameType.getByName(gameTypeId)
                .orElse(null);
        if (gameType == null) {
            return getInvalidGameTypeResponse(gameTypeId);
        }

        GameState gameState = gameStateService.findForGame(gameType, gameId)
                .orElse(null);
        if (gameState == null) {
            return new ResponseBuilder()
                    .withError(String.format("No game found for %s with ID %d", gameType.getName(), gameId))
                    .build();
        }

        return new ResponseBuilder()
                .withBody(gameState)
                .build();
    }

    @GetMapping("/games/state/api/new-game-id/get/{gameType}")
    public ResponsePayload getNewGameId(@PathVariable(name = "gameType") String gameTypeId) {
        long newGameId = GameType.getByName(gameTypeId)
                .map(gameStateService::getNewGameId)
                .orElse(1l);

        return new ResponseBuilder()
                .withBody(newGameId)
                .build();
    }

    private ResponsePayload getInvalidGameTypeResponse(String gameTypeId) {
        return new ResponseBuilder()
                .withError(String.format("No GameType found for \"%s\"", gameTypeId))
                .withStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}
