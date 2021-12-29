package com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces;

import com.kiwiko.library.lang.random.RandomUtil;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakCard;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakGame;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakPlayer;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakEndTurnRequest;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakEndTurnResponse;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakSelectHandCardRequest;
import com.kiwiko.jdashboard.webapp.apps.games.state.api.GameStateService;
import com.kiwiko.jdashboard.webapp.apps.games.state.data.GameState;
import com.kiwiko.jdashboard.webapp.framework.json.gson.GsonProvider;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PazaakGameHandler {
    private static final int MAX_WINNING_SCORE_THRESHOLD = 20;

    @Inject private PazaakGameLoader gameLoader;
    @Inject private GameStateService gameStateService;
    @Inject private GsonProvider gsonProvider;
    @Inject private RandomUtil randomUtil;

    public int calculatePlayerScore(PazaakPlayer player) {
        return player.getPlacedCards().stream()
                .mapToInt(PazaakCard::getModifier)
                .sum();
    }

    public Optional<PazaakPlayer> calculateWinner(PazaakGame game) {
        Optional<PazaakPlayer> outscoringWinner = getOutscoringWinner(game);
        if (outscoringWinner.isPresent()) {
            return outscoringWinner;
        }

        // TODO

        return Optional.empty();
    }

    public PazaakEndTurnResponse endTurn(PazaakEndTurnRequest request) {
        // Request validation.
        PazaakGame game = gameLoader.loadGame(request).orElse(null);

        if (game == null) {
            return new PazaakEndTurnResponse()
                    .setErrorMessage(String.format("No game found matching loading parameters %s", request));
        }

        Objects.requireNonNull(request.getPlayerId(), "Player ID is required to end turn");
        validateTurn(game, request.getPlayerId());

        // End the actual turn.
        if (Objects.equals(request.getPlayerId(), game.getPlayer().getId())) {
            endTurnForPlayer(game, request);
        } else {
            endTurnForOpponent(game, request);
        }

        PazaakPlayer player = getPlayerById(game, request.getPlayerId());
        PazaakPlayer oppositePlayer = getOppositePlayer(game, player.getId());

        if (playerHasBusted(player)) {
            setGameWinner(game, oppositePlayer.getId());
        }

        // Switch turns if necessary.
        player.setPlayerStatus(PazaakPlayerStatuses.END_TURN);
        if (!Objects.equals(oppositePlayer.getPlayerStatus(), PazaakPlayerStatuses.STAND)) {
            oppositePlayer.setPlayerStatus(PazaakPlayerStatuses.READY);
            game.setCurrentPlayerId(oppositePlayer.getId());
        }

        calculateWinner(game)
                .map(PazaakPlayer::getId)
                .ifPresent(winningPlayerId -> setGameWinner(game, winningPlayerId));

        saveGame(game);

        return new PazaakEndTurnResponse()
                .setGame(game);
    }

    public PazaakGame selectHandCard(PazaakSelectHandCardRequest request) throws PazaakGameException {
        Objects.requireNonNull(request.getPlayerId(), "Player ID is required to select hand card");
        Objects.requireNonNull(request.getSelectedHandCard(), "Hand card is required");

        PazaakGame game = gameLoader.loadGame(request).orElse(null);
        if (game == null) {
            throw new PazaakGameException("No game found");
        }

        PazaakPlayer player = getPlayerById(game, request.getPlayerId());
        if (!player.getHandCards().contains(request.getSelectedHandCard())) {
            throw new PazaakGameException("Hand card not found");
        }

        player.getHandCards().remove(request.getSelectedHandCard());
        player.getPlacedCards().add(request.getSelectedHandCard());

        // Don't save.
        return game;
    }

    private Optional<PazaakPlayer> getOutscoringWinner(PazaakGame game) {
        boolean allPlayersStanding = Stream.of(game.getPlayer(), game.getOpponent())
                .map(PazaakPlayer::getPlayerStatus)
                .allMatch(PazaakPlayerStatuses.STAND::equals);

        if (!allPlayersStanding) {
            return Optional.empty();
        }

        int playerScore = calculatePlayerScore(game.getPlayer());
        int opponentScore = calculatePlayerScore(game.getOpponent());

        if (opponentScore > MAX_WINNING_SCORE_THRESHOLD) {
            return Optional.of(game.getPlayer());
        }

        if (playerScore > MAX_WINNING_SCORE_THRESHOLD) {
            return Optional.of(game.getOpponent());
        }

        return Optional.empty();
    }

    private void endTurnForPlayer(PazaakGame game, PazaakEndTurnRequest request) {
        PazaakCard placedCard;
        if (request.getPlayedCard() == null) {
            placedCard = createRandomCard();
        } else {
            placedCard = request.getPlayedCard();
            boolean removedFromHand = game.getPlayer().getHandCards().remove(placedCard);
            if (!removedFromHand) {
                throw new PazaakGameException("Expected placed card to come from hand");
            }
        }

        game.getPlayer().getPlacedCards().add(placedCard);
    }

    private void endTurnForOpponent(PazaakGame game, PazaakEndTurnRequest request) {
        PazaakCard placedCard = getOpponentCardToEndTurn(game)
                .orElseGet(this::createRandomCard);
        game.getOpponent().getPlacedCards().add(placedCard);
    }

    private Optional<PazaakCard> getOpponentCardToEndTurn(PazaakGame game) {
        int playerScore = calculatePlayerScore(game.getPlayer());
        int minimumRiskThreshold = Math.max(playerScore, MAX_WINNING_SCORE_THRESHOLD - 2);
        int opponentScore = calculatePlayerScore(game.getOpponent());
        Map<Integer, PazaakCard> cardsByOutcomeScore = game.getOpponent().getHandCards().stream()
                .collect(Collectors.toMap(card -> card.getModifier() + opponentScore, Function.identity(), (a, b) -> a));

        PazaakCard handCardToWin = cardsByOutcomeScore.get(MAX_WINNING_SCORE_THRESHOLD);
        if (handCardToWin != null) {
            game.getOpponent().getHandCards().remove(handCardToWin);
            return Optional.of(handCardToWin);
        }

        return cardsByOutcomeScore.entrySet().stream()
                .filter(entry -> (entry.getKey() >= minimumRiskThreshold) && (entry.getKey() <= MAX_WINNING_SCORE_THRESHOLD))
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue);
    }

    private PazaakPlayer getPlayerById(PazaakGame game, String playerId) throws PazaakGameException {
        return Stream.of(game.getPlayer(), game.getOpponent())
                .filter(player -> Objects.equals(player.getId(), playerId))
                .findFirst()
                .orElseThrow(() -> new PazaakGameException(String.format("No players found matching ID %s", playerId)));
    }

    private PazaakPlayer getOppositePlayer(PazaakGame game, String playerId) {
        return Objects.equals(game.getPlayer().getId(), playerId)
                ? game.getOpponent()
                : game.getPlayer();
    }

    private boolean playerHasBusted(PazaakPlayer player) {
        return calculatePlayerScore(player) > MAX_WINNING_SCORE_THRESHOLD;
    }

    private void setGameWinner(PazaakGame game, String playerId) {
        game.setWinningPlayerId(playerId);

        game.getPlayer().setPlayerStatus(null);
        game.getOpponent().setPlayerStatus(null);
    }

    private void validateTurn(PazaakGame game, String currentPlayerId) throws PazaakGameException {
        if (!Objects.equals(game.getCurrentPlayerId(), currentPlayerId)) {
            throw new PazaakGameException("Current player does not match");
        }

        PazaakPlayer player = getPlayerById(game, currentPlayerId);
        if (!Objects.equals(player.getPlayerStatus(), PazaakPlayerStatuses.READY)) {
            throw new PazaakGameException("Player is not ready");
        }
    }

    private void saveGame(PazaakGame game) {
        GameState gameState = Optional.ofNullable(game.getGameId())
                .flatMap(gameId -> gameStateService.findForGame(PazaakGameProperties.GAME_TYPE_ID, gameId))
                .orElseThrow(() -> new PazaakGameException("No pazaak game state found"));

        String gameStateJson = gsonProvider.getDefault().toJson(game);
        gameState.setGameStateJson(gameStateJson);

        gameStateService.update(gameState);
    }

    private PazaakCard createRandomCard() {
        int modifier = randomUtil.rollDice(10) + 1; // [1, 10]
        PazaakCard card = new PazaakCard();
        card.setModifier(modifier);

        return card;
    }
}
