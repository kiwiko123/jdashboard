package com.kiwiko.jdashboard.clients.sessions.api.interfaces;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientException;

public interface SessionClient {

    ApiResponse<GetSessionsOutput> get(GetSessionsInput input) throws ApiClientException, InterruptedException;

    InvalidateSessionOutput invalidate(InvalidateSessionInput input) throws ApiClientException, InterruptedException;
}
