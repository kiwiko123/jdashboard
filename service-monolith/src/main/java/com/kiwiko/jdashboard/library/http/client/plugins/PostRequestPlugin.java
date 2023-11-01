package com.kiwiko.jdashboard.library.http.client.plugins;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.exceptions.ApiClientPluginException;

public interface PostRequestPlugin extends ApiClientPlugin {

    <ResponseType> void postRequest(ApiRequest apiRequest, ApiResponse<ResponseType> apiResponse) throws ApiClientPluginException;
}
