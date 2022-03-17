package com.kiwiko.jdashboard.library.http.client.api.interfaces.plugins;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ApiClientPluginException;

public interface PreRequestPlugin extends ApiClientPlugin {

    void preRequest(ApiRequest apiRequest) throws ApiClientPluginException;
}
