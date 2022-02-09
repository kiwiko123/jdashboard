package com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces;

import com.kiwiko.jdashboard.library.http.client.api.exceptions.JdashboardApiClientException;

public interface SessionClient {

    GetSessionsByTokensOutput getByTokens(GetSessionsByTokensInput input)
            throws JdashboardApiClientException, InterruptedException;
}
