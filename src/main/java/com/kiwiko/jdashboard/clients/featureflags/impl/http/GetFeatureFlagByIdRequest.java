package com.kiwiko.jdashboard.clients.featureflags.impl.http;

import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;

class GetFeatureFlagByIdRequest extends GetFeatureFlagRequest {
    private final RequestUrl requestUrl;

    public GetFeatureFlagByIdRequest(long featureFlagId) {
        UriBuilder uriBuilder = new UriBuilder()
                .setPath(String.format("/feature-flags/service-api/%d", featureFlagId));
        requestUrl = RequestUrl.fromPartial(uriBuilder);
    }

    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }
}
