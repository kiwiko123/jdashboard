package com.kiwiko.jdashboard.servicerequestkeys.client.impl.http;

import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.client.caching.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

class ProvisionServiceRequestKeyRequest extends JdashboardApiRequest {
    private final RequestUrl requestUrl;
    private final ProvisionServiceRequestKeyInput input;
    private final RequestCacheStrategy cacheStrategy;

    public ProvisionServiceRequestKeyRequest(ProvisionServiceRequestKeyInput input) {
        requestUrl = RequestUrl.fromPartial(
                new UriBuilder()
                        .setPath("/developers/public-api/v1/service-request-keys"));
        this.input = input;
        cacheStrategy = new DisabledCacheStrategy();
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return ProvisionServiceRequestKeyOutput.class;
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return input;
    }

    @Nullable
    @Override
    public Duration getRequestTimeout() {
        return Duration.ofSeconds(3);
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return cacheStrategy;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }
}
