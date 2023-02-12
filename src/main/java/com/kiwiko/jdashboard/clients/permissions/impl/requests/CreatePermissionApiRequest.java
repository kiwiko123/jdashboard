package com.kiwiko.jdashboard.clients.permissions.impl.requests;

import com.kiwiko.jdashboard.clients.permissions.api.interfaces.parameters.CreatePermissionInput;
import com.kiwiko.jdashboard.clients.permissions.api.interfaces.parameters.CreatePermissionOutput;
import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.caching.DisabledCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.caching.RequestCacheStrategy;
import com.kiwiko.jdashboard.library.http.client.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.JdashboardServiceClientIdentifiers;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class CreatePermissionApiRequest extends JdashboardApiRequest {

    private final CreatePermissionInput createPermissionInput;

    public CreatePermissionApiRequest(CreatePermissionInput createPermissionInput) {
        this.createPermissionInput = createPermissionInput;
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Override
    public RequestUrl getRequestUrl() {
        return RequestUrl.fromPartial(new UriBuilder().setPath("/permissions/service-api"));
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return createPermissionInput;
    }

    @Override
    public Set<RequestHeader> getRequestHeaders() {
        return Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON);
    }

    @Override
    public RequestCacheStrategy getCacheStrategy() {
        return new DisabledCacheStrategy();
    }

    @Nullable
    @Override
    public String getClientIdentifier() {
        return JdashboardServiceClientIdentifiers.DEFAULT;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return CreatePermissionOutput.class;
    }
}
