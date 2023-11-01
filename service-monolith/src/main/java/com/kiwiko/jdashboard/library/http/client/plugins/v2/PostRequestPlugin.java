package com.kiwiko.jdashboard.library.http.client.plugins.v2;

import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;

public interface PostRequestPlugin extends ApiClientRequestPlugin {

    <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>, ResponseType>
        void postRequest(RequestType request, RequestContextType requestContext, ApiResponse<ResponseType> response) throws ClientException, ServerException;
}
