package com.kiwiko.jdashboard.clients.featureflags.impl.http;

import com.kiwiko.jdashboard.clients.featureflags.api.interfaces.parameters.GetFeatureFlagOutput;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;

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

    @Override
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return GetFeatureFlagOutput.class;
    }
}
