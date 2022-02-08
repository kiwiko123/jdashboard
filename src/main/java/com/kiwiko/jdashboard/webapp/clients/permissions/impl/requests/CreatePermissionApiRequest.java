package com.kiwiko.jdashboard.webapp.clients.permissions.impl.requests;

import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.CreatePermissionInput;
import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.parameters.CreatePermissionOutput;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.api.constants.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestHeader;
import com.kiwiko.jdashboard.library.http.client.api.dto.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class CreatePermissionApiRequest extends DefaultApiRequest {

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
        return RequestUrl.fromPartial(new UriBuilder().setPath("/internal-api/permissions"));
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
    public boolean isInternalServiceRequest() {
        return true;
    }

    @Nullable
    @Override
    public Class<?> getResponseType() {
        return CreatePermissionOutput.class;
    }
}
