package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.queries.GetUsersQuery;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.library.http.client.api.dto.ApiResponse;
import com.kiwiko.library.http.client.api.interfaces.JdashboardApiClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import java.util.Arrays;

@CrossOriginConfigured
@Controller
public class PlaygroundController {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private UserClient userClient;

    @GetMapping("/playground-api/test")
    public ResponsePayload test() throws Exception {
        GetUsersByQueryResponse response = userClient.getByQuery(GetUsersQuery.newBuilder().setUserIds(Arrays.asList(1L, 2L, 3L)).build());;
        return ResponseBuilder.payload(response);
    }
}
