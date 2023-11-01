package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.exceptions.ApiClientPluginException;
import com.kiwiko.jdashboard.library.http.client.plugins.PreRequestPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class LoggingPreRequestPlugin implements PreRequestPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingPreRequestPlugin.class);

    @Override
    public void preRequest(ApiRequest apiRequest) throws ApiClientPluginException {
        LOGGER.info(
                "[JdashboardApiClient] {} request to \"{}\"",
                apiRequest.getRequestMethod().name(),
                apiRequest.getRequestUrl().toUrlString());
    }
}
