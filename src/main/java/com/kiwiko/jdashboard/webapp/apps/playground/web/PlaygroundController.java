package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUserByIdResponse;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.PushService;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.exceptions.PushException;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters.PushToClientParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;

//@JdashboardController
@CrossOriginConfigured
@Controller
public class PlaygroundController {

    @Inject private UserClient userClient;
    @Inject private PushService pushService;

    @GetMapping("/playground-api/test")
    public ResponsePayload test() {
        GetUserByIdResponse result = userClient.getById(3L);
        return ResponseBuilder.payload(result);
    }
}
