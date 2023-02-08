package com.kiwiko.jdashboard.clients.tablerecordversions.impl.http.requests;

import com.kiwiko.jdashboard.clients.tablerecordversions.api.interfaces.parameters.GetLastUpdatedOutput;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.url.QueryParameter;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.library.http.url.UrlQuery;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardServiceClientIdentifiers;

import javax.annotation.Nullable;
import java.util.Collection;

public class GetLastUpdatedVersionsRequest extends JdashboardApiRequest {

    private final GetLastUpdatedVerisonsRequestCacheStrategy cacheStrategy;
    private final RequestUrl requestUrl;

    public GetLastUpdatedVersionsRequest(Collection<String> serializedVersionRecords) {
        cacheStrategy = new GetLastUpdatedVerisonsRequestCacheStrategy();

        UrlQuery.Builder urlQueryBuilder = UrlQuery.newBuilder();
        serializedVersionRecords.stream()
                .map(value -> QueryParameter.withEncodedValue("vr", value))
                .forEach(urlQueryBuilder::addQueryParameter);

        requestUrl = RequestUrl.fromPartial(new UriBuilder()
                .setPath("/table-record-versions/service-api/last-updated")
                .setQuery(urlQueryBuilder.build()));
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
        return GetLastUpdatedOutput.class;
    }
}
