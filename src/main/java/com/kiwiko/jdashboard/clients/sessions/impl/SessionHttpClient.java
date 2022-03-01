package com.kiwiko.jdashboard.clients.sessions.impl;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientException;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsOutput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.InvalidateSessionInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.InvalidateSessionOutput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.SessionClient;
import com.kiwiko.jdashboard.clients.sessions.impl.requests.GetSessionsRequest;
import com.kiwiko.jdashboard.clients.sessions.impl.requests.InvalidateSessionRequest;
import com.kiwiko.jdashboard.tools.httpclient.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;

public class SessionHttpClient implements SessionClient {

    @Inject private JdashboardApiClient jdashboardApiClient;

    @Override
    public ApiResponse<GetSessionsOutput> get(GetSessionsInput input) throws ApiClientException, InterruptedException {
        GetSessionsRequest request = new GetSessionsRequest(input);
        return jdashboardApiClient.synchronousCall(request);
    }

    @Override
    public InvalidateSessionOutput invalidate(InvalidateSessionInput input) throws ApiClientException, InterruptedException {
        InvalidateSessionRequest request = new InvalidateSessionRequest(input);
        ApiResponse<InvalidateSessionOutput> response = jdashboardApiClient.synchronousCall(request);
        return response.getPayload();
    }
}
