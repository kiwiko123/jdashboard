package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2;

import com.kiwiko.jdashboard.library.http.client.RequestHeaders;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;

public class JsonContentTypeHeaderPreRequestPlugin implements PreRequestPlugin {

    @Override
    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>> void preRequest(RequestType request, RequestContextType requestContext) throws ClientException {
        switch (request.getRequestMethod()) {
            case POST:
            case PUT:
                request.addRequestHeader(RequestHeaders.CONTENT_TYPE_JSON);
        }
    }
}
