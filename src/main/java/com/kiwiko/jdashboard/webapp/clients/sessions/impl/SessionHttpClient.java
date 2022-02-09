package com.kiwiko.jdashboard.webapp.clients.sessions.impl;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.JdashboardApiClientException;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.GetSessionsByTokensInput;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.GetSessionsByTokensOutput;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.SessionClient;
import com.kiwiko.jdashboard.webapp.clients.sessions.impl.requests.GetSessionsByTokensRequest;
import com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;
import java.util.Objects;

public class SessionHttpClient implements SessionClient {

    @Inject private JdashboardApiClient jdashboardApiClient;

    @Override
    public GetSessionsByTokensOutput getByTokens(GetSessionsByTokensInput input)
            throws JdashboardApiClientException, InterruptedException {
        Objects.requireNonNull(input.getTokens(), "Input tokens are required");
        GetSessionsByTokensRequest request = new GetSessionsByTokensRequest(input.getTokens());
        ApiResponse<GetSessionsByTokensOutput> response = jdashboardApiClient.synchronousCall(request);
        return response.getPayload();
    }
}
