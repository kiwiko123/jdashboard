package com.kiwiko.jdashboard.webapp.clients.users.impl.http.requests;

import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.responses.GetUsersByQueryResponse;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;

import javax.annotation.Nullable;

public class GetUsersByQueryApiRequest extends DefaultApiRequest {

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
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return GetUsersByQueryResponse.class;
    }
}
