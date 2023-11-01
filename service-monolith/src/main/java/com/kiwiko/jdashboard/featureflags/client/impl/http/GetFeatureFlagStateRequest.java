package com.kiwiko.jdashboard.featureflags.client.impl.http;

import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;

class GetFeatureFlagStateRequest extends JdashboardApiRequest {
    private final RequestUrl requestUrl;

    public GetFeatureFlagStateRequest(String featureFlagName, @Nullable Long userId) {
        UrlQuery.Builder urlQueryBuilder = UrlQuery.newBuilder()
                .addQueryParameter(QueryParameter.withRawValue("fn", featureFlagName));

        if (userId != null) {
            urlQueryBuilder.addQueryParameter(QueryParameter.withRawValue("u", userId.toString()));
        }

        UriBuilder uriBuilder = new UriBuilder()
                .setPath("/feature-flags/service-api/state")
                .setQuery(urlQueryBuilder.build());

        requestUrl = RequestUrl.fromPartial(uriBuilder);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
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
