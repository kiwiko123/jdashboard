package com.kiwiko.games.state.web;

import com.kiwiko.games.state.api.GameStateService;
import com.kiwiko.games.state.data.GameState;
import com.kiwiko.games.state.data.GameType;
import com.kiwiko.mvc.json.data.ResponseBuilder;
import com.kiwiko.mvc.json.data.ResponsePayload;
import com.kiwiko.mvc.security.environments.api.EnvironmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@CrossOrigin(origins = EnvironmentService.CROSS_ORIGIN_DEV_URL)
@RestController
public class GameStateAPIController {

    @Inject
    private GameStateService gameStateService;

    @GetMapping("/games/state/api/get/{gameType}/{gameId}")
    public ResponseEntity<ResponsePayload> getGameStateForGame(
            @PathVariable(name = "gameType") String gameTypeId,
            @PathVariable(name = "gameId") long gameId) {
        GameType gameType = GameType.getById(gameTypeId)
                .orElse(null);
        if (gameType == null) {
            return getInvalidGameTypeResponse(gameTypeId);
        }

        GameState gameState = gameStateService.findForGame(gameType, gameId)
                .orElse(null);
        if (gameState == null) {
            return new ResponseBuilder()
                    .withError(String.format("No game found for %s with ID %d", gameType.getName(), gameId))
                    .toResponseEntity();
        }

        return new ResponseBuilder()
                .withBody(gameState)
                .toResponseEntity();
    }

    @GetMapping("/games/state/api/new-game-id/get/{gameType}")
    public ResponseEntity<ResponsePayload> getNewGameId(@PathVariable(name = "gameType") String gameTypeId) {
        long newGameId = GameType.getById(gameTypeId)
                .map(gameStateService::getNewGameId)
                .orElse(1l);

        return new ResponseBuilder()
                .withBody(newGameId)
                .toResponseEntity();
    }

    private ResponseEntity<ResponsePayload> getInvalidGameTypeResponse(String gameTypeId) {
        return new ResponseBuilder()
                .withError(String.format("No GameType found for \"%s\"", gameTypeId))
                .withStatus(HttpStatus.BAD_REQUEST)
                .toResponseEntity();
    }
}
