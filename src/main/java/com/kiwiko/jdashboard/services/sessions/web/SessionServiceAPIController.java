package com.kiwiko.jdashboard.services.sessions.web;

import com.kiwiko.jdashboard.clients.sessions.api.dto.Session;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.CreateSessionInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.CreateSessionOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.LockedApi;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionService;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsOutput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.InvalidateSessionOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardServiceClientIdentifiers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sessions/service-api")
@JdashboardConfigured
@LockedApi(clients = JdashboardServiceClientIdentifiers.DEFAULT)
public class SessionServiceAPIController {

    @Inject private SessionService sessionService;

    @GetMapping("/sessions")
    public GetSessionsOutput get(
            @RequestParam(value = "id", required = false) @Nullable Set<Long> sessionIds,
            @RequestParam(value = "t", required = false) @Nullable Set<String> tokens,
            @RequestParam(value = "ia", required = false) @Nullable Boolean isActive) {
        GetSessionsInput.Builder builder = GetSessionsInput.newBuilder()
                .setIsActive(isActive);

        if (sessionIds != null) {
            builder.setSessionIds(sessionIds);
        }
        if (tokens != null) {
            Set<String> decodedTokens = tokens.stream()
                    .map(token -> URLDecoder.decode(token, StandardCharsets.UTF_8))
                    .collect(Collectors.toSet());
            builder.setTokens(decodedTokens);
        }

        return sessionService.get(builder.build());
    }

    @PostMapping("/{id}/invalidate")
    public InvalidateSessionOutput invalidateSessionById(@PathVariable("id") long sessionId) {
        Session session = sessionService.invalidateSession(sessionId);

        InvalidateSessionOutput output = new InvalidateSessionOutput();
        output.setSession(session);

        return output;
    }

    @PostMapping("/users/create")
    public CreateSessionOutput createSessionForUser(@RequestBody CreateSessionInput input) {
        Session session = sessionService.createSessionForUser(input.getUserId());
        CreateSessionOutput output = new CreateSessionOutput();
        output.setSession(session);

        return output;
    }
}
