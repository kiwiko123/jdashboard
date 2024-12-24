package com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins.srk;

import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GetInternalServiceRequestKeyRequest extends HttpApiRequest {
    private final RequestUrl requestUrl;
    private final ProvisionServiceRequestKeyInput provisionServiceRequestKeyInput;

    public GetInternalServiceRequestKeyRequest(ProvisionServiceRequestKeyInput provisionServiceRequestKeyInput) {
        requestUrl = RequestUrl.fromPartial(
                new UriBuilder()
                        .setPath("/developers/internal-api/v1/service-request-keys"));
        this.provisionServiceRequestKeyInput = provisionServiceRequestKeyInput;
    }

    @Nonnull
    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }

    @Nonnull
    @Override
    public RequestUrl getRequestUrl() {
        return requestUrl;
    }

    @Nullable
    @Override
    public Object getRequestBody() {
        return provisionServiceRequestKeyInput;
    }
}
