package com.kiwiko.jdashboard.webapp.application.events.internal.streaming;

import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.client.caching.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardServiceClientIdentifiers;

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

    @Nullable
    @Override
    public String getClientIdentifier() {
        return JdashboardServiceClientIdentifiers.DEFAULT;
    }
}
