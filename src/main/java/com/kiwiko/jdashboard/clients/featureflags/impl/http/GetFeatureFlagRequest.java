package com.kiwiko.jdashboard.clients.featureflags.impl.http;

import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;

abstract class GetFeatureFlagRequest extends JdashboardApiRequest {

    private final GetFeatureFlagCacheStrategy cacheStrategy;

    public GetFeatureFlagRequest() {
        cacheStrategy = new GetFeatureFlagCacheStrategy();
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return cacheStrategy;
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return "feature-flag-service-client";
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return GetFeatureFlagOutput.class;
    }
}
