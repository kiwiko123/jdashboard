package com.kiwiko.jdashboard.webapp.clients.users.impl.http.requests;

import com.kiwiko.jdashboard.webapp.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.library.http.url.QueryParameter;
import com.kiwiko.library.http.url.UriBuilder;
import com.kiwiko.library.http.url.UrlQuery;

import javax.annotation.Nullable;
import java.util.Set;

public class GetUserByQueryApiRequest extends DefaultApiRequest {

    private final String queryJson;

    public GetUserByQueryApiRequest(String queryJson) {
//        super();
        this.queryJson = queryJson;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public UriBuilder getUriBuilder() {
        return new UriBuilder()
                .setPath("/users/api/internal/query")
                .setQuery(
                        UrlQuery.newBuilder()
                                .addQueryParameter(QueryParameter.withEncodedValue("query", queryJson))
                                .build());
    }

    @Override
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public <ResponseType> Class<ResponseType> getResponseType() {
        return (Class<ResponseType>) Set.class;
    }
}
