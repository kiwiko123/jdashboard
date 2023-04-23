package com.kiwiko.jdashboard.clients.sessions.impl.requests;

import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsOutput;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;

public class GetSessionsRequest extends JdashboardApiRequest {

    private final RequestUrl requestUrl;
    private final GetSessionsRequestCacheStrategy cacheStrategy;

    public GetSessionsRequest(GetSessionsInput input) {
        requestUrl = makeRequestUrl(input);
        cacheStrategy = new GetSessionsRequestCacheStrategy();
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return cacheStrategy;
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return "session-service-client";
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return GetSessionsOutput.class;
    }

    private RequestUrl makeRequestUrl(GetSessionsInput input) {
        UriBuilder uriBuilder = new UriBuilder()
                .setPath("/sessions/service-api/sessions");

        UrlQuery.Builder queryBuilder = UrlQuery.newBuilder();

        if (input.getSessionIds() != null) {
            input.getSessionIds().stream()
                    .map(id -> Long.toString(id))
                    .map(id -> QueryParameter.withRawValue("id", id))
                    .forEach(queryBuilder::addQueryParameter);
        }
        if (input.getTokens() != null) {
            input.getTokens().stream()
                    .map(token -> QueryParameter.withEncodedValue("t", token))
                    .forEach(queryBuilder::addQueryParameter);
        }
        if (input.getIsActive() != null) {
            queryBuilder.addQueryParameter(QueryParameter.withRawValue("ia", Boolean.toString(input.getIsActive())));
        }

        uriBuilder.setQuery(queryBuilder.build());

        return RequestUrl.fromPartial(uriBuilder);
    }
}
