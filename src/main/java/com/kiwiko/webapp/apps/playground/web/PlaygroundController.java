package com.kiwiko.webapp.apps.playground.web;

import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.api.errors.ServerException;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.caching.RequestCachePolicy;
import com.kiwiko.webapp.http.client.impl.JdashboardHttpClient;
import com.kiwiko.webapp.mvc.controllers.JdashboardController;
import com.kiwiko.webapp.mvc.json.api.ResponseBuilder;
import com.kiwiko.webapp.mvc.json.data.ResponsePayload;
import com.kiwiko.webapp.mvc.security.authentication.api.annotations.CrossOriginConfigured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import java.time.Duration;

@JdashboardController
public class PlaygroundController {

    @Inject private JdashboardHttpClient jdashboardHttpClient;

    @GetMapping("/playground-api/test")
    public ResponsePayload test() throws InterruptedException, ClientException, ServerException {
        GetRequest request = GetRequest.newBuilder()
                .setUrl("/feature-flags/api/1")
                .setTimeout(Duration.ofSeconds(10))
                .setIsInternalServiceRequest(true)
                .build();

        for (int i = 0; i < 10; ++i) {
            GetRequest r = GetRequest.newBuilder()
                    .setUrl("/feature-flags/api/test")
                    .setTimeout(Duration.ofSeconds(1))
                    .setCachePolicy(RequestCachePolicy.newBuilder().setDuration(Duration.ofSeconds(5)).build())
                    .setIsInternalServiceRequest(true)
                    .build();

            jdashboardHttpClient.syncGet(r, String.class);
        }

        return ResponseBuilder.ok();
    }
}
