package com.kiwiko.jdashboard.tools.apiclient.impl.http.plugins;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientPluginException;
import com.kiwiko.jdashboard.library.http.client.api.interfaces.plugins.PostRequestPlugin;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.caching.ApiClientCache;

import javax.inject.Inject;

public class ResponseCachingPostRequestPlugin implements PostRequestPlugin {

    @Inject private ApiClientCache apiClientCache;

    @Override
    public <ResponseType> void postRequest(ApiRequest apiRequest, ApiResponse<ResponseType> apiResponse) throws ApiClientPluginException {
        apiClientCache.processRequestCaching(apiRequest, apiResponse);
    }
}
