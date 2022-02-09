package com.kiwiko.jdashboard.webapp.clients.sessions.impl.requests;

import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;
import com.kiwiko.jdashboard.webapp.clients.sessions.api.interfaces.GetSessionsByTokensOutput;

import javax.annotation.Nullable;
import java.util.Set;

public class GetSessionsByTokensRequest extends DefaultApiRequest {

    private final Set<String> tokens;

    public GetSessionsByTokensRequest(Set<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        UriBuilder uriBuilder = new UriBuilder()
                .setPath("/sessions/service-api/tokens");

        UrlQuery.Builder urlQueryBuilder = UrlQuery.newBuilder();
        tokens.stream()
                .map(token -> QueryParameter.withEncodedValue("t", token))
                .forEach(urlQueryBuilder::addQueryParameter);
        uriBuilder.setQuery(urlQueryBuilder.build());

        return RequestUrl.fromPartial(uriBuilder);
    }

    @Override
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return GetSessionsByTokensOutput.class;
    }
}
