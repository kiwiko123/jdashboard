package com.kiwiko.jdashboard.services.sessions.web;

import com.kiwiko.jdashboard.services.sessions.api.dto.Session;
import com.kiwiko.jdashboard.services.sessions.api.interfaces.SessionService;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.GetSessionsByTokensOutput;
import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Set;

@RestController
@RequestMapping("/sessions/service-api")
@JdashboardConfigured
//@InternalServiceCheck
public class SessionServiceAPIController {

    @Inject private SessionService sessionService;

    @GetMapping("/tokens")
    public GetSessionsByTokensOutput getByTokens(@RequestParam("t") Set<String> tokens) {
        Set<Session> sessions = sessionService.getByTokens(tokens);

        GetSessionsByTokensOutput output = new GetSessionsByTokensOutput();
        output.setSessions(sessions);

        return output;
    }
}
