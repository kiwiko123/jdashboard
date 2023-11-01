package com.kiwiko.jdashboard.library.http.client.plugins.v2;

import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;

public interface PreRequestPlugin extends ApiClientRequestPlugin {

    <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>>
        void preRequest(RequestType request, RequestContextType requestContext) throws ClientException;
}
