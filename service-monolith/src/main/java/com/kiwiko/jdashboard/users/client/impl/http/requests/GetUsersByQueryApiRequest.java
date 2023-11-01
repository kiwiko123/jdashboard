package com.kiwiko.jdashboard.users.client.impl.http.requests;

import com.kiwiko.jdashboard.users.client.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;

public class GetUsersByQueryApiRequest extends JdashboardApiRequest {

    private final String queryJson;

    public GetUsersByQueryApiRequest(String queryJson) {
        this.queryJson = queryJson;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromPartial(
                new UriBuilder()
                .setPath("/users/api/internal/query")
                .setQuery(
                        UrlQuery.newBuilder()
                                .addQueryParameter(QueryParameter.withEncodedValue("query", queryJson))
                                .build()));
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new GetUsersByQueryCacheStrategy();
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return "user-service-client";
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return GetUsersByQueryResponse.class;
    }
}
