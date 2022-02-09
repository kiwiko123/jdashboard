package com.kiwiko.jdashboard.services.sessions.web;

import com.kiwiko.jdashboard.services.sessions.api.dto.Session;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionService;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.GetSessionsByTokensOutput;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.checks.InternalServiceCheck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sessions/service-api")
@JdashboardConfigured
@InternalServiceCheck
public class SessionServiceAPIController {

    @Inject private SessionService sessionService;

    @GetMapping("/tokens")
    public GetSessionsByTokensOutput getByTokens(@RequestParam("t") Set<String> encodedTokens) {
        Set<String> decodedTokens = encodedTokens.stream()
                .map(token -> URLDecoder.decode(token, StandardCharsets.UTF_8))
                .collect(Collectors.toSet());
        Set<Session> sessions = sessionService.getByTokens(decodedTokens);

        GetSessionsByTokensOutput output = new GetSessionsByTokensOutput();
        output.setSessions(sessions);

        return output;
    }
}
