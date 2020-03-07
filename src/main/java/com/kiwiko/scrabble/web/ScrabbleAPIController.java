package com.kiwiko.scrabble.web;

import com.kiwiko.mvc.annotations.RequestBodyCollectionParameter;
import com.kiwiko.mvc.annotations.RequestBodyParameter;
import com.kiwiko.mvc.json.data.ResponseBuilder;
import com.kiwiko.mvc.json.data.ResponsePayload;
import com.kiwiko.scrabble.api.ScrabbleGameService;
import com.kiwiko.scrabble.game.ScrabbleGame;
import com.kiwiko.scrabble.game.ScrabblePlayer;
import com.kiwiko.scrabble.game.ScrabbleSubmittedTile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ScrabbleAPIController {

    @Inject
    private ScrabbleGameService scrabbleGameService;

    @RequestMapping(path = "/scrabble/api/new-game", method = RequestMethod.GET)
    public ResponseEntity<ResponsePayload> newGame() {
        return new ResponseBuilder()
                .withBody(scrabbleGameService.createGame())
                .toResponseEntity();
    }

    @RequestMapping(path = "/scrabble/api/submit-tiles", method = RequestMethod.POST)
    public ResponseEntity<ResponsePayload> submitTiles(
            @RequestBodyParameter(name = "gameId") long gameId,
            @RequestBodyParameter(name = "player") ScrabblePlayer player) {
        ScrabbleGame game = scrabbleGameService.getGameById(gameId);
        return new ResponseBuilder()
                .withBody(game)
                .toResponseEntity();
    }

    @RequestMapping(path = "/scrabble/api/validate-move", method = RequestMethod.POST)
    public ResponseEntity<ResponsePayload> getInvalidSubmittedTiles(
            @RequestBodyParameter(name = "gameId") long gameId,
            @RequestBodyCollectionParameter(name = "tiles", valueType = ScrabbleSubmittedTile.class) List<ScrabbleSubmittedTile> submittedTiles) {
        ScrabbleGame game = scrabbleGameService.getGameById(gameId);
        Collection<ScrabbleSubmittedTile> invalidTiles = scrabbleGameService.getInvalidTiles(game, submittedTiles);

        if (invalidTiles.isEmpty()) {
            return ResponseBuilder.ok();
        }

        return new ResponseBuilder()
                .withBody(invalidTiles)
                .withError("Invalid tiles")
                .toResponseEntity();
    }
}
