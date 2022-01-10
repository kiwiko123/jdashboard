package com.kiwiko.jdashboard.webapp.apps.games.pazaak.web;

import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.dto.PazaakGame;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameCreator;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameException;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameHandler;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.PazaakGameLoader;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakCreateNewGameParameters;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakEndTurnRequest;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakLoadGameParameters;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakEndTurnResponse;
import com.kiwiko.jdashboard.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakSelectHandCardRequest;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticatedUser;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.webapp.users.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@Controller
@JdashboardConfigured
@RequestMapping("/pazaak/api")
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
public class PazaakGameController {

    @Inject private PazaakGameCreator gameCreator;
    @Inject private PazaakGameLoader gameLoader;
    @Inject private PazaakGameHandler gameHandler;

    @GetMapping("/games/{gameId}")
    @ResponseBody
    public PazaakGame loadGameForUser(
            @PathVariable("gameId") long gameId,
            @RequestParam(value = "userId", required = false) @Nullable Long userId,
            RequestContext requestContext) {
        Long currentUserId = Optional.ofNullable(userId)
                .or(() -> Optional.ofNullable(requestContext.getUserId()))
                .orElseThrow(() -> new PazaakGameException("No user provided to load game"));

        PazaakLoadGameParameters parameters = new PazaakLoadGameParameters()
                .setGameId(gameId)
                .setUserId(currentUserId);

        return gameLoader.loadGame(parameters).orElse(null);
    }

    @PostMapping("")
    @ResponseBody
    public PazaakGame createNewGame(
            @RequestBody PazaakCreateNewGameParameters parameters,
            @AuthenticatedUser com.kiwiko.jdashboard.webapp.clients.users.api.dto.User currentUser) {
        Objects.requireNonNull(parameters.getPlayerId(), "Player ID is required");
        Objects.requireNonNull(parameters.getOpponentId(), "Opponent ID is required");

        parameters.setPlayerUserId(currentUser.getId());

        return gameCreator.createNewGame(parameters);
    }

    @PostMapping("/games/{gameId}/end-turn")
    @ResponseBody
    public PazaakEndTurnResponse endTurn(
            @PathVariable("gameId") long gameId,
            @RequestBody PazaakEndTurnRequest request,
            @AuthenticatedUser com.kiwiko.jdashboard.webapp.clients.users.api.dto.User currentUser) {
        request.setGameId(gameId);
        request.setUserId(currentUser.getId());

        return gameHandler.endTurn(request);
    }

    @PostMapping("/games/{gameId}/select-hand-card")
    @ResponseBody
    public PazaakGame selectHandCard(
            @PathVariable("gameId") long gameId,
            @RequestBody PazaakSelectHandCardRequest request,
            @AuthenticatedUser com.kiwiko.jdashboard.webapp.clients.users.api.dto.User currentUser) {
        request.setGameId(gameId);
        request.setUserId(currentUser.getId());

        return gameHandler.selectHandCard(request);
    }
}
