package com.kiwiko.jdashboard.featureflags.client.impl.http;

import com.kiwiko.jdashboard.featureflags.client.api.dto.ResolvedFeatureFlag;
import com.kiwiko.jdashboard.featureflags.client.api.interfaces.parameters.TurnOnFeatureFlagInput;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;

import javax.annotation.Nullable;

class TurnOnFeatureFlagRequest extends AbstractToggleFeatureFlagRequest {
    private final RequestUrl requestUrl;
    private final TurnOnFeatureFlagInput input;

    public TurnOnFeatureFlagRequest(TurnOnFeatureFlagInput input) {
        requestUrl = RequestUrl.fromPartial(new UriBuilder().setPath("/feature-flags/service-api/state/on"));
        this.input = input;
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

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return ResolvedFeatureFlag.class;
    }
}
