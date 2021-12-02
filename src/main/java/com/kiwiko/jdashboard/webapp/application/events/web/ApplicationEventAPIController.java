package com.kiwiko.jdashboard.webapp.application.events.web;

import com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.JdashboardConfigured;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@JdashboardConfigured
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
public class ApplicationEventAPIController {

    @Inject private ApplicationEventService applicationEventService;

    @GetMapping("/application-events/api/{id}")
    public ApplicationEvent getById(@PathVariable("id") Long id) {
        return applicationEventService.get(id).orElse(null);
    }

    @PostMapping("/application-events/api")
    public ApplicationEvent createEvent(@RequestBody ApplicationEvent event) {
        return applicationEventService.create(event);
    }
}
