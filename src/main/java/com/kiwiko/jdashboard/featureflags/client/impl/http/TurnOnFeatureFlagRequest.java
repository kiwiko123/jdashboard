package com.kiwiko.jdashboard.featureflags.client.impl.http;

import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.TurnOnFeatureFlagInput;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;

import javax.annotation.Nullable;

class TurnOnFeatureFlagRequest extends AbstractToggleFeatureFlagRequest {
    private final RequestUrl requestUrl;
    private final TurnOnFeatureFlagInput input;

    public TurnOnFeatureFlagRequest(String featureFlagName, @Nullable Long userId) {
        requestUrl = RequestUrl.fromPartial(new UriBuilder().setPath("/feature-flags/service-api/state/on"));
        input = new TurnOnFeatureFlagInput(featureFlagName, userId);
    }

    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return input;
    }
}
