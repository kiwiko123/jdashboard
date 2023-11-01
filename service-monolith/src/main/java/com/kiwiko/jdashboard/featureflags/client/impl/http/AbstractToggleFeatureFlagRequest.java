package com.kiwiko.jdashboard.featureflags.client.impl.http;

import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

abstract class AbstractToggleFeatureFlagRequest extends JdashboardApiRequest {
    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return "feature-flag-service-client";
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return ResolvedFeatureFlag.class;
    }
}
