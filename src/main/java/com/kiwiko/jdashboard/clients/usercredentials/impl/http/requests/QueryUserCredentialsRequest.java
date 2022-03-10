package com.kiwiko.jdashboard.clients.usercredentials.impl.http.requests;

import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.clients.usercredentials.api.interfaces.parameters.QueryUserCredentialsOutput;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;

import javax.annotation.Nullable;

public class QueryUserCredentialsRequest extends JdashboardApiRequest {

    private final QueryUserCredentialsInput input;

    public QueryUserCredentialsRequest(QueryUserCredentialsInput input) {
        this.input = input;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        UrlQuery.Builder urlQueryBuilder = UrlQuery.newBuilder();

        if (input.getUserIds() != null) {
            input.getUserIds().stream()
                    .map(userId -> QueryParameter.withRawValue("u", userId.toString()))
                    .forEach(urlQueryBuilder::addQueryParameter);
        }

        if (input.getCredentialTypes() != null) {
            input.getCredentialTypes().stream()
                    .map(credentialType -> QueryParameter.withEncodedValue("ct", credentialType))
                    .forEach(urlQueryBuilder::addQueryParameter);
        }

        if (input.getIsRemoved() != null) {
            urlQueryBuilder.addQueryParameter(QueryParameter.withRawValue("ir", input.getIsRemoved().toString()));
        }

        return RequestUrl.fromPartial(
                new UriBuilder()
                        .setPath("/user-credentials/service-api/query")
                        .setQuery(urlQueryBuilder.build()));
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
        return QueryUserCredentialsOutput.class;
    }
}
