package com.kiwiko.jdashboard.clients.sessions.api.interfaces;

import com.kiwiko.jdashboard.library.http.client.exceptions.ApiClientException;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;

public interface SessionClient {

    ClientResponse<GetSessionsOutput> get(GetSessionsInput input) throws ApiClientException, InterruptedException;

    InvalidateSessionOutput invalidate(InvalidateSessionInput input) throws ApiClientException, InterruptedException;

    ClientResponse<CreateSessionOutput> create(CreateSessionInput input);
}
