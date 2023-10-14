package com.kiwiko.jdashboard.tools.apiclient.impl.http.security.plugins.srk;

import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.RequestMethod;
import com.kiwiko.jdashboard.library.http.client.RequestUrl;
import com.kiwiko.jdashboard.library.http.url.UriBuilder;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;

import java.util.Collections;

public class GetInternalServiceRequestKeyRequest extends HttpApiRequest {

    public GetInternalServiceRequestKeyRequest(ProvisionServiceRequestKeyInput input) {
        RequestUrl requestUrl = RequestUrl.fromPartial(
                new UriBuilder()
                        .setPath("/developers/internal-api/v1/service-request-keys"));

        setRequestMethod(RequestMethod.POST);
        setRequestUrl(requestUrl);
        setRequestBody(input);
        setRequestHeaders(Collections.singleton(RequestHeaders.CONTENT_TYPE_JSON));
    }
}
