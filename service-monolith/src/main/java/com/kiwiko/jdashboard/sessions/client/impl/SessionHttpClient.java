package com.kiwiko.jdashboard.sessions.client.impl;

import com.kiwiko.jdashboard.sessions.client.api.interfaces.CreateSessionInput;
import com.kiwiko.jdashboard.sessions.client.api.interfaces.CreateSessionOutput;
import com.kiwiko.jdashboard.sessions.client.impl.requests.CreateSessionRequest;
import com.kiwiko.jdashboard.library.http.client.exceptions.ApiClientException;
import com.kiwiko.jdashboard.sessions.client.api.interfaces.GetSessionsInput;
import com.kiwiko.jdashboard.sessions.client.api.interfaces.GetSessionsOutput;
import com.kiwiko.jdashboard.sessions.client.api.interfaces.InvalidateSessionInput;
import com.kiwiko.jdashboard.sessions.client.api.interfaces.InvalidateSessionOutput;
import com.kiwiko.jdashboard.sessions.client.api.interfaces.SessionClient;
import com.kiwiko.jdashboard.sessions.client.impl.requests.GetSessionsRequest;
import com.kiwiko.jdashboard.sessions.client.impl.requests.InvalidateSessionRequest;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;

import javax.inject.Inject;

public class SessionHttpClient implements SessionClient {

    @Inject private JdashboardApiClient jdashboardApiClient;

    @Override
    public ClientResponse<GetSessionsOutput> get(GetSessionsInput input) throws ApiClientException, InterruptedException {
        GetSessionsRequest request = new GetSessionsRequest(input);
        return jdashboardApiClient.synchronousCall(request);
    }

    @Override
    public InvalidateSessionOutput invalidate(InvalidateSessionInput input) throws ApiClientException, InterruptedException {
        InvalidateSessionRequest request = new InvalidateSessionRequest(input);
        ClientResponse<InvalidateSessionOutput> response = jdashboardApiClient.synchronousCall(request);
        return response.getPayload();
    }

    @Override
    public ClientResponse<CreateSessionOutput> create(CreateSessionInput input) {
        CreateSessionRequest request = new CreateSessionRequest(input);
        return jdashboardApiClient.silentSynchronousCall(request);
    }
}
