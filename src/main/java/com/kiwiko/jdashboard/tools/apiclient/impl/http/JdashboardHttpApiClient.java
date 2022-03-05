package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;

import javax.inject.Inject;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class JdashboardHttpApiClient implements JdashboardApiClient {

    @Inject private CoreHttpClient httpClient;
    @Inject private ApiClientRequestHelper requestHelper;
    @Inject private ApiClientResponseHelper responseHelper;
    @Inject private ApiClientCache apiClientCache;

    @Override
    public <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request)
            throws ClientException, ServerException, InterruptedException {
        requestHelper.validateRequest(request);
        Optional<ApiResponse<ResponseType>> cachedResponse = apiClientCache.getCachedResponse(request);
        if (cachedResponse.isPresent()) {
            return cachedResponse.get();
        }

        HttpRequest httpRequest = requestHelper.makeHttpRequest(request);
        HttpResponse<String> httpResponse = httpClient.sendSynchronousRequest(httpRequest);

        return responseHelper.convertHttpResponse(request, httpResponse);
    }

    @Override
    public <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request)
            throws ClientException, ServerException, InterruptedException {
        requestHelper.validateRequest(request);
        Optional<ApiResponse<ResponseType>> cachedResponse = apiClientCache.getCachedResponse(request);
        if (cachedResponse.isPresent()) {
            return CompletableFuture.completedFuture(cachedResponse.get());
        }

        HttpRequest httpRequest = requestHelper.makeHttpRequest(request);
        CompletableFuture<HttpResponse<String>> httpResponseFuture = httpClient.sendAsynchronousRequest(httpRequest);

        return responseHelper.transformResponseFuture(request, httpResponseFuture);
    }
}
