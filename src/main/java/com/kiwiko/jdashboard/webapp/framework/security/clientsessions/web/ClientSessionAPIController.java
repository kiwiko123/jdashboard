package com.kiwiko.jdashboard.webapp.framework.security.clientsessions.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserAuthCheck;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.dto.ClientSession;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces.ClientSessionService;
import com.kiwiko.jdashboard.webapp.framework.security.clientsessions.api.interfaces.parameters.CreateClientSessionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@JdashboardConfigured
@RequestMapping("/client-sessions/api")
public class ClientSessionAPIController {

    @Inject private ClientSessionService clientSessionService;

    @UserAuthCheck
    @GetMapping("/{id}")
    public ClientSession getById(@PathVariable("id") Long id) {
        return clientSessionService.get(id).orElse(null);
    }

    @PostMapping("")
    public CreateClientSessionResponse createSession() {
        return clientSessionService.createNewSession();
    }

    @PostMapping("/{uuid}/end")
    public ClientSession endSession(
            @PathVariable("uuid") String uuid) {
        return clientSessionService.endSession(uuid);
    }
}
