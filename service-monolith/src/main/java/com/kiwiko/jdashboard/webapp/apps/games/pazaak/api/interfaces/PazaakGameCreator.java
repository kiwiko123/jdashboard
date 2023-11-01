package com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces;

import com.kiwiko.jdashboard.library.lang.random.RandomUtil;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakCard;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakGame;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakPlayer;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakCreateNewGameParameters;
import com.kiwiko.jdashboard.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.jdashboard.webapp.apps.games.state.data.GameState;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PazaakGameCreator {
    @Inject private GameStateService gameStateService;
    @Inject private RandomUtil randomUtil;
    @Inject private GsonProvider gsonProvider;

    public PazaakGame createNewGame(PazaakCreateNewGameParameters parameters) {
        PazaakPlayer player = createPlayerWithRandomCards(parameters.getPlayerId());
        PazaakPlayer opponent = createPlayerWithRandomCards(parameters.getOpponentId());

        player.setPlayerStatus(PazaakPlayerStatuses.READY);

        PazaakGame game = new PazaakGame();
        game.setPlayer(player);
        game.setOpponent(opponent);
        game.setCurrentPlayerId(player.getId());

        GameState gameState = saveNewGame(game, parameters.getPlayerUserId());
        game.setGameId(gameState.getGameId());

        return game;
    }

    public PazaakPlayer createPlayerWithRandomCards(String playerId) {
        Set<PazaakCard> handCards = IntStream.range(0, 4)
                .mapToObj(i -> createRandomHandCard())
                .collect(Collectors.toSet());

        PazaakPlayer player = new PazaakPlayer();
        player.setId(playerId);
        player.setHandCards(handCards);

        return player;
    }

    private PazaakCard createRandomHandCard() {
        int modifier = randomUtil.rollDice(6) + 1;
        if (randomUtil.flipCoin()) {
            modifier *= -1;
        }

        PazaakCard card = new PazaakCard();
        card.setModifier(modifier);

        return card;
    }

    private GameState saveNewGame(PazaakGame game, Long userId) {
        long gameId = gameStateService.getNewGameId(PazaakGameProperties.GAME_TYPE_ID);
        game.setGameId(gameId);
        String gameJson = gsonProvider.getDefault().toJson(game);

        GameState gameState = new GameState();
        gameState.setGameType(PazaakGameProperties.GAME_TYPE_ID);
        gameState.setGameStateJson(gameJson);
        gameState.setGameId(gameId);

        return gameStateService.saveForUser(gameState, userId);
    }
}
