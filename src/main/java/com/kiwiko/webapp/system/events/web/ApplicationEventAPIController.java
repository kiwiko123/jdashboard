package com.kiwiko.webapp.system.events.web;

import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationLevel;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.AuthenticationRequired;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.webapp.system.events.api.interfaces.ApplicationEventService;
import com.kiwiko.webapp.system.events.api.dto.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@CrossOriginConfigured
@RestController
@AuthenticationRequired(levels = AuthenticationLevel.AUTHENTICATED)
public class ApplicationEventAPIController {

    @Inject private ApplicationEventService applicationEventService;

    @GetMapping("/application-events/api/{id}")
    public ResponsePayload getById(@PathVariable("id") Long id) {
        ApplicationEvent event = applicationEventService.get(id).orElse(null);
        return ResponseBuilder.payload(event);
    }

    @PostMapping("/application-events/api")
    public ResponsePayload createEvent(@RequestBody ApplicationEvent event) {
        ApplicationEvent createdEvent = applicationEventService.create(event);
        return ResponseBuilder.payload(createdEvent);
    }
}
