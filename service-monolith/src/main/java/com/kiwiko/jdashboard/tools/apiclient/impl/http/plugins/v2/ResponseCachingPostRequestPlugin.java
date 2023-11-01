package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2;

import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PostRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.caching.ApiClientCache;

import javax.inject.Inject;

public class ResponseCachingPostRequestPlugin implements PostRequestPlugin {
    @Inject private ApiClientCache apiClientCache;

    @Override
    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>, ResponseType>
        void postRequest(RequestType request, RequestContextType requestContext, ApiResponse<ResponseType> response) throws ClientException, ServerException {
        apiClientCache.processRequestCaching(request, requestContext, response);
    }
}
