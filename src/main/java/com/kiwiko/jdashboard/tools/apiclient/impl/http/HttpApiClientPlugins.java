package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientPluginException;
import com.kiwiko.jdashboard.library.http.client.api.interfaces.plugins.ApiClientPlugins;
import com.kiwiko.jdashboard.library.http.client.api.interfaces.plugins.PostRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.api.interfaces.plugins.PreRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.LoggingPreRequestPlugin;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class HttpApiClientPlugins {
    private final ApiClientPlugins apiClientPlugins;

    @Inject
    public HttpApiClientPlugins(LoggingPreRequestPlugin loggingPreRequestPlugin) {
        apiClientPlugins = new ApiClientPlugins(List.of(loggingPreRequestPlugin), Collections.emptyList());
    }

    public void runPreRequestPlugins(ApiRequest apiRequest) throws ApiClientPluginException {
        for (PreRequestPlugin plugin : apiClientPlugins.getPreRequestPlugins()) {
            try {
                plugin.preRequest(apiRequest);
            } catch (Exception e) {
                throw new ApiClientPluginException(String.format("Error running pre-request plugin %s", plugin.toString()), e);
            }
        }
    }

    public <ResponseType> void runPostRequestPlugins(ApiRequest apiRequest, ApiResponse<ResponseType> apiResponse) throws ApiClientPluginException {
        for (PostRequestPlugin plugin : apiClientPlugins.getPostRequestPlugins()) {
            try {
                plugin.postRequest(apiRequest, apiResponse);
            } catch (Exception e) {
                throw new ApiClientPluginException(String.format("Error running post-request plugin %s", plugin.toString()), e);
            }
        }
    }
}
