package com.kiwiko.jdashboard.webapp.application.events.internal.streaming;

import com.kiwiko.jdashboard.library.http.client.api.constants.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.client.api.dto.caching.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class HttpEmitApplicationEventRequest extends JdashboardApiRequest {

    private final EmitApplicationEventRequest request;
    private final RequestUrl requestUrl;

    public HttpEmitApplicationEventRequest(EmitApplicationEventRequest request, String url) {
        this.request = request;

        UriBuilder uriBuilder = new UriBuilder()
                .setPath(url);
        requestUrl = RequestUrl.fromPartial(uriBuilder);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.PUT;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new DisabledCacheStrategy();
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return request;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }

    @Override
    public boolean isInternalServiceRequest() {
        return !request.isDisableRequestSecurity();
    }
}
