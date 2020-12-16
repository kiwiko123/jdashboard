package com.kiwiko.webapp.games.scrabble.web;

import com.kiwiko.webapp.games.scrabble.game.data.ScrabbleTile;
import com.kiwiko.webapp.games.scrabble.game.logic.ScrabbleCreateGameHelper;
import com.kiwiko.webapp.games.scrabble.game.logic.ScrabbleGameHelper;
import com.kiwiko.webapp.games.scrabble.game.logic.ScrabbleMoveHelper;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.requests.api.annotations.RequestBodyCollectionParameter;
import com.kiwiko.webapp.mvc.requests.api.annotations.RequestBodyParameter;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.games.scrabble.api.ScrabbleGameService;
import com.kiwiko.webapp.games.scrabble.game.data.ScrabbleGame;
import com.kiwiko.webapp.games.scrabble.game.data.ScrabbleSubmittedTile;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.webapp.users.api.UserService;
import com.kiwiko.webapp.users.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@CrossOriginConfigured
@RestController
public class ScrabbleAPIController {

    @Inject
    private ScrabbleGameService scrabbleGameService;

    @Inject
    private ScrabbleGameHelper scrabbleGameHelper;

    @Inject
    private ScrabbleCreateGameHelper scrabbleCreateGameHelper;

    @Inject
    private ScrabbleMoveHelper scrabbleMoveHelper;

    @Inject
    private UserService userService;

    @GetMapping("/scrabble/api/start-game")
    public ResponsePayload startGame(
            @RequestParam(value = "gameId", required = false) @Nullable Long gameId,
            RequestContext requestContext) {
        User user = requestContext.getUser().orElse(null);
        ScrabbleGame game = scrabbleCreateGameHelper.getOrCreateGame(user, gameId);
        return new ResponseBuilder()
                .withBody(game)
                .build();
    }

    @GetMapping(path = "/scrabble/api/new-game")
    public ResponsePayload newGame() {
        ScrabbleGame game = scrabbleCreateGameHelper.createGame();
        return new ResponseBuilder()
                .withBody(game)
                .build();
    }

    @GetMapping("/scrabble/api/load-game/{gameId}")
    public ResponsePayload loadGame(
            @PathVariable(name = "gameId") long gameId) {
        ScrabbleGame game = scrabbleGameService.getGameById(gameId)
                .orElse(null);
        if (game == null) {
            return new ResponseBuilder()
                    .withError("Game not found")
                    .build();
        }

        return new ResponseBuilder()
                .withBody(game)
                .build();
    }

    @PostMapping(path = "/scrabble/api/submit-tiles")
    public ResponsePayload submitTiles(
            @RequestBodyParameter(name = "gameId") long gameId,
            @RequestBodyCollectionParameter(name = "tiles", valueType = ScrabbleSubmittedTile.class) List<ScrabbleSubmittedTile> tiles) {
        ScrabbleGame game = scrabbleGameService.getGameById(gameId)
                .orElse(null);
        if (game == null) {
            return gameNotFoundResponse(gameId);
        }

        boolean success = scrabbleGameHelper.placeTiles(game, tiles);
        if (success) {
            scrabbleGameService.saveGame(game);
        } else {
            return new ResponseBuilder()
                    .withError("Invalid tiles")
                    .build();
        }

        return new ResponseBuilder()
                .withBody(game)
                .build();
    }

    @PostMapping(path = "/scrabble/api/validate-move")
    public ResponsePayload validateMove(
            @RequestBodyParameter(name = "gameId") long gameId,
            @RequestBodyCollectionParameter(name = "tiles", valueType = ScrabbleSubmittedTile.class) List<ScrabbleSubmittedTile> submittedTiles) {
        ScrabbleGame game = scrabbleGameService.getGameById(gameId)
                .orElse(null);
        if (game == null) {
            return gameNotFoundResponse(gameId);
        }

        Collection<ScrabbleSubmittedTile> invalidTiles = scrabbleMoveHelper.getInvalidTiles(game, submittedTiles);
        if (!invalidTiles.isEmpty()) {
            return new ResponseBuilder()
                    .withBody(invalidTiles)
                    .withError("Invalid tiles")
                    .build();
        }

        String word = submittedTiles.stream()
                .map(ScrabbleTile::getCharacter)
                .collect(Collectors.joining());
        boolean isValidWord = scrabbleMoveHelper.isValidWord(word);
        if (!isValidWord) {
            return new ResponseBuilder()
                    .withError(String.format("\"%s\" is not a word", word))
                    .build();
        }

        return ResponseBuilder.ok();
    }

    @GetMapping("/scrabble/api/find-game/most-recent/by-user/{userId}")
    public ResponsePayload findMostRecentGameByUser(@PathVariable("userId") Long userId) {
        ScrabbleGame game = scrabbleGameService.findMostRecentGameForUser(userId)
                .orElse(null);
        return new ResponseBuilder()
                .withBody(game)
                .build();
    }

    @PostMapping("/scrabble/api/save-game")
    public ResponsePayload saveGameForUser(
            @RequestBodyParameter(name = "gameId") long gameId,
            @RequestBodyParameter(name = "userId") long userId) {
        User user = userService.getById(userId)
                .orElse(null);
        if (user == null) {
            return new ResponseBuilder()
                    .withError("No matching user found")
                    .build();
        }

        ScrabbleGame game = scrabbleGameService.getGameById(gameId)
                .orElse(null);
        if (game == null) {
            return gameNotFoundResponse(gameId);
        }

        scrabbleGameService.saveGameForUser(gameId, userId);
        return ResponseBuilder.ok();
    }

    private ResponsePayload gameNotFoundResponse(long gameId) {
        return new ResponseBuilder()
                .withError(String.format("No game found with ID %d", gameId))
                .withStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}
