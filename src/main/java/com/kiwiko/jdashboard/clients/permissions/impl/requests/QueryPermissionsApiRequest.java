package com.kiwiko.jdashboard.clients.permissions.impl.requests;

import com.kiwiko.jdashboard.clients.permissions.api.interfaces.parameters.QueryPermissionsInput;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;

import javax.annotation.Nullable;

public class QueryPermissionsApiRequest extends JdashboardApiRequest {

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
                    .setPath("/permissions/service-api/query")
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
        return CorePermissionSet.class;
    }
}
