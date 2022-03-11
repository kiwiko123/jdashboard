package com.kiwiko.jdashboard.tools.apiclient.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultApiRequest;

import java.time.Duration;

public abstract class JdashboardApiRequest extends DefaultApiRequest {

    @Override
    public Duration getRequestTimeout() {
        // Generous default timeout.
        return Duration.ofSeconds(30);
    }
}
