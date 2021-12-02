package com.kiwiko.jdashboard.webapp.apps.playground.web;

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

    @Inject private PushService pushService;

    @GetMapping("/playground-api/test")
    public ResponsePayload test() {
        PushToClientParameters parameters = new PushToClientParameters();
        parameters.setServiceId("jdashboard-notifications");
        parameters.setUserId(1L);
        parameters.setRecipientUserId(1L);
        parameters.setData("{\"message\":\"test\"}");

        try {
            pushService.pushToClient(parameters);
        } catch (PushException e) {
            throw new RuntimeException(e);
        }

        return ResponseBuilder.ok();
    }
}
