package com.kiwiko.jdashboard.library.http.client.api.interfaces.plugins;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientPluginException;

public interface PostRequestPlugin extends ApiClientPlugin {

    <ResponseType> void postRequest(ApiRequest apiRequest, ApiResponse<ResponseType> apiResponse) throws ApiClientPluginException;
}
