package com.kiwiko.jdashboard.clients.sessions.impl.requests;

import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsInput;
import com.kiwiko.jdashboard.clients.sessions.api.interfaces.GetSessionsOutput;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;

import javax.annotation.Nullable;

public class GetSessionsRequest extends JdashboardApiRequest {

    private final GetSessionsInput input;

    public GetSessionsRequest(GetSessionsInput input) {
        this.input = input;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
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

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new DisabledCacheStrategy();
    }

    @Override
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return GetSessionsOutput.class;
    }
}
