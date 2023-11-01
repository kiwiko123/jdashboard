package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.exceptions.ApiClientPluginException;
import com.kiwiko.jdashboard.library.http.client.plugins.ApiClientPlugins;
import com.kiwiko.jdashboard.library.http.client.plugins.PostRequestPlugin;
import com.kiwiko.jdashboard.library.http.client.plugins.PreRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.LoggingPreRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins.ResponseCachingPostRequestPlugin;

import javax.inject.Inject;
import java.util.List;

public class HttpApiClientPlugins {
    private final ApiClientPlugins apiClientPlugins;

    @Inject
    public HttpApiClientPlugins(
            LoggingPreRequestPlugin loggingPreRequestPlugin,
            ResponseCachingPostRequestPlugin responseCachingPostRequestPlugin) {
        apiClientPlugins = new ApiClientPlugins(
                List.of(loggingPreRequestPlugin),
                List.of(responseCachingPostRequestPlugin));
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
