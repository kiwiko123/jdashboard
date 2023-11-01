package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.v2;

import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;
import com.kiwiko.jdashboard.library.http.client.plugins.v2.PreRequestPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLoggerPreRequestPlugin implements PreRequestPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggerPreRequestPlugin.class);

    @Override
    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>> void preRequest(RequestType request, RequestContextType requestContext) throws ClientException {
        LOGGER.info(
                "[JdashboardApiClient] {} request to \"{}\"",
                request.getRequestMethod().name(),
                request.getRequestUrl().toUrlString());
    }
}
