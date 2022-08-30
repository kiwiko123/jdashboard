package com.kiwiko.jdashboard.clients.featureflags.impl.http;

import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;

import javax.annotation.Nullable;

class GetFeatureFlagByNameRequest extends GetFeatureFlagRequest {
    private final RequestUrl requestUrl;

    public GetFeatureFlagByNameRequest(String featureFlagName, @Nullable Long userId) {
        UrlQuery.Builder urlQueryBuilder = UrlQuery.newBuilder()
                .addQueryParameter(QueryParameter.withEncodedValue("fn", featureFlagName));
        if (userId != null) {
            urlQueryBuilder.addQueryParameter(QueryParameter.withRawValue("u", userId.toString()));
        }

        UriBuilder uriBuilder = new UriBuilder()
                .setPath("/feature-flags/service-api")
                .setQuery(urlQueryBuilder.build());
        requestUrl = RequestUrl.fromPartial(uriBuilder);
    }

    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }
}
