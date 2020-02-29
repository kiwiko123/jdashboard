package com.kiwiko.scrabble.web;

import com.kiwiko.mvc.annotations.RequestBodyCollectionParameter;
import com.kiwiko.mvc.annotations.RequestBodyParameter;
import com.kiwiko.scrabble.api.ScrabbleGameService;
import com.kiwiko.scrabble.game.ScrabbleGame;
import com.kiwiko.scrabble.game.ScrabblePlayer;
import com.kiwiko.scrabble.game.ScrabbleSubmittedTile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ScrabbleAPIController {

    @Inject
    private ScrabbleGameService scrabbleGameService;

    @RequestMapping(path = "/scrabble/api/new-game", method = RequestMethod.GET)
    public ScrabbleGame newGame() {
        return scrabbleGameService.createGame();
    }

    @RequestMapping(path = "/scrabble/api/submit-tiles", method = RequestMethod.POST)
    public ScrabbleGame submitTiles(@RequestBodyParameter(name = "gameId") long gameId,
                                    @RequestBodyParameter(name = "player") ScrabblePlayer player) {
        ScrabbleGame game = scrabbleGameService.getGameById(gameId);
        return game;
    }

    @RequestMapping(path = "/scrabble/api/validate-move", method = RequestMethod.POST)
    public boolean isSubmissionValid(@RequestBodyParameter(name = "gameId") long gameId,
                                     @RequestBodyCollectionParameter(name = "tiles", valueType = ScrabbleSubmittedTile.class) List<ScrabbleSubmittedTile> submittedTiles) {
        ScrabbleGame game = scrabbleGameService.getGameById(gameId);
        return scrabbleGameService.areTilesValid(game, submittedTiles);
    }
}
