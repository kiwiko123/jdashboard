package com.kiwiko.jdashboard.webapp.clients.permissions.impl.requests;

import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.library.http.client.api.constants.RequestMethod;
import com.kiwiko.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.library.http.client.api.dto.RequestUrl;
import com.kiwiko.library.http.url.QueryParameter;
import com.kiwiko.library.http.url.UriBuilder;
import com.kiwiko.library.http.url.UrlQuery;

import javax.annotation.Nullable;

public class QueryPermissionsApiRequest extends DefaultApiRequest {

    private final QueryPermissionsInput queryPermissionsInput;

    public QueryPermissionsApiRequest(QueryPermissionsInput queryPermissionsInput) {
        this.queryPermissionsInput = queryPermissionsInput;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }

    @Override
    public RequestUrl getRequestUrl() {
        UrlQuery.Builder urlQueryBuilder = UrlQuery.newBuilder();
        if (queryPermissionsInput.getUserIds() != null) {
            queryPermissionsInput.getUserIds().stream()
                    .map(userId -> QueryParameter.withEncodedValue("u", userId.toString()))
                    .forEach(urlQueryBuilder::addQueryParameter);
        }

        if (queryPermissionsInput.getPermissionNames() != null) {
            queryPermissionsInput.getPermissionNames().stream()
                    .map(permissionName -> QueryParameter.withEncodedValue("pn", permissionName))
                    .forEach(urlQueryBuilder::addQueryParameter);
        }

        return RequestUrl.fromPartial(
                new UriBuilder()
                    .setPath("/internal-api/permissions/query")
                    .setQuery(urlQueryBuilder.build()));
    }

    @Override
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return CorePermissionSet.class;
    }
}
