package com.kiwiko.jdashboard.tablerecordversions.client.impl.http.requests;

import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.CreateTableRecordVersionInput;
import com.kiwiko.jdashboard.tablerecordversions.client.api.interfaces.parameters.CreateTableRecordVersionOutput;
import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.client.caching.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class CreateTableRecordVersionRequest extends JdashboardApiRequest {

    private final CreateTableRecordVersionInput input;

    public CreateTableRecordVersionRequest(CreateTableRecordVersionInput input) {
        this.input = input;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromPartial(new UriBuilder().setPath("/table-record-versions/service-api"));
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new DisabledCacheStrategy();
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return input;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return "table-record-version-service-client";
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return CreateTableRecordVersionOutput.class;
    }
}
