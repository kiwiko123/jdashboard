package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientPluginException;
import com.kiwiko.jdashboard.library.http.client.api.interfaces.plugins.PreRequestPlugin;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;

public class LoggingPreRequestPlugin implements PreRequestPlugin {

    @Inject private Logger logger;

    @Override
    public void preRequest(ApiRequest apiRequest) throws ApiClientPluginException {
        logger.info(
                "[JdashboardApiClient - {}] {} request to \"{}\"",
                getClass().getSimpleName(),
                apiRequest.getRequestMethod().name(),
                apiRequest.getRequestUrl().toUrlString());
    }
}
