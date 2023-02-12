package com.kiwiko.jdashboard.library.http.client.plugins;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.exceptions.ApiClientPluginException;

public interface PreRequestPlugin extends ApiClientPlugin {

    void preRequest(ApiRequest apiRequest) throws ApiClientPluginException;
}
