package com.kiwiko.webapp.apps.games.pazaak.web;

import com.kiwiko.webapp.apps.games.pazaak.api.dto.PazaakGame;
import com.kiwiko.webapp.apps.games.pazaak.api.interfaces.PazaakGameCreator;
import com.kiwiko.webapp.apps.games.pazaak.api.interfaces.PazaakGameException;
import com.kiwiko.webapp.apps.games.pazaak.api.interfaces.parameters.PazaakCreateNewGameParameters;
import com.kiwiko.webapp.mvc.requests.data.RequestContext;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.webapp.users.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Objects;

@Controller
@RequestMapping("/pazaak/api")
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
@CrossOriginConfigured
public class PazaakGameController {

    @Inject private PazaakGameCreator gameCreator;

    @PostMapping("")
    @ResponseBody
    public PazaakGame createNewGame(
            @RequestBody PazaakCreateNewGameParameters parameters,
            RequestContext requestContext) {
        Objects.requireNonNull(parameters.getPlayerId(), "Player ID is required");
        Objects.requireNonNull(parameters.getOpponentId(), "Opponent ID is required");

        Long userId = requestContext.getUser()
                .map(User::getId)
                .orElseThrow(() -> new PazaakGameException("No logged in user found"));
        parameters.setPlayerUserId(userId);

        return gameCreator.createNewGame(parameters);
    }
}
