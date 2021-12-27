package com.kiwiko.jdashboard.webapp.http.client.impl.apiclient;

import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.JdashboardApiClientException;
import com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class JdashboardHttpApiClient implements JdashboardApiClient {

    @Inject private ApiClientHttpClient httpClient;
    @Inject private ApiClientRequestHelper requestHelper;
    @Inject private ApiClientResponseHelper responseHelper;

    @Override
    public <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request)
            throws InterruptedException, JdashboardApiClientException {
        requestHelper.validateRequest(request);
        HttpRequest httpRequest = requestHelper.makeHttpRequest(request);
        HttpResponse<String> httpResponse = httpClient.sendSynchronousRequest(httpRequest);

        return responseHelper.convertHttpResponse(request, httpResponse);
    }

    @Override
    public <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request)
            throws InterruptedException, JdashboardApiClientException {
        requestHelper.validateRequest(request);
        return null;
    }
}
