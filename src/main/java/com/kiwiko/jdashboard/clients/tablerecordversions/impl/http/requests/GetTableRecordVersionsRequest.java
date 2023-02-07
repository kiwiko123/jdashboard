package com.kiwiko.jdashboard.clients.tablerecordversions.impl.http.requests;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetTableRecordVersionOutput;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.client.api.dto.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.services.tablerecordversions.api.interfaces.parameters.GetTableRecordVersions;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardServiceClientIdentifiers;

import javax.annotation.Nullable;

public class GetTableRecordVersionsRequest extends JdashboardApiRequest {

    private final GetTableRecordVerisonsRequestCacheStrategy cacheStrategy;
    private final RequestUrl requestUrl;

    public GetTableRecordVersionsRequest(GetTableRecordVersions input) {
        cacheStrategy = new GetTableRecordVerisonsRequestCacheStrategy();
        requestUrl = RequestUrl.fromPartial(
                new UriBuilder()
                        .setPath(String.format("/table-record-versions/service-api/%s/%d", input.getTableName(), input.getRecordId())));
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
        return JdashboardServiceClientIdentifiers.DEFAULT;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return GetTableRecordVersionOutput.class;
    }
}
