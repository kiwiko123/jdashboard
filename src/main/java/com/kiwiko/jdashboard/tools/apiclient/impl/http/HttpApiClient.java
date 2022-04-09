package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;
import com.kiwiko.jdashboard.library.http.client.api.interfaces.ApiClient;
import com.kiwiko.jdashboard.library.http.client.impl.CoreHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.impl.http.caching.ApiClientCache;

import javax.inject.Inject;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class HttpApiClient implements ApiClient {

    @Inject private CoreHttpClient httpClient;
    @Inject private ApiClientRequestHelper requestHelper;
    @Inject private ApiClientResponseHelper responseHelper;
    @Inject private ApiClientCache apiClientCache;
    @Inject private HttpApiClientPlugins httpApiClientPlugins;

    @Override
    public <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request)
            throws ClientException, ServerException, InterruptedException {
        requestHelper.validateRequest(request);
        Optional<ApiResponse<ResponseType>> cachedResponse = apiClientCache.getCachedResponse(request);
        if (cachedResponse.isPresent()) {
            return cachedResponse.get();
        }

        HttpRequest httpRequest = requestHelper.makeHttpRequest(request);
        httpApiClientPlugins.runPreRequestPlugins(request);
        HttpResponse<String> httpResponse = httpClient.sendSynchronousRequest(httpRequest);

        ApiResponse<ResponseType> apiResponse = responseHelper.convertHttpResponse(request, httpResponse);
        httpApiClientPlugins.runPostRequestPlugins(request, apiResponse);

        return apiResponse;
    }

    @Override
    public <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request) throws ClientException {
        requestHelper.validateRequest(request);
        Optional<ApiResponse<ResponseType>> cachedResponse = apiClientCache.getCachedResponse(request);
        if (cachedResponse.isPresent()) {
            return CompletableFuture.completedFuture(cachedResponse.get());
        }

        HttpRequest httpRequest = requestHelper.makeHttpRequest(request);
        httpApiClientPlugins.runPreRequestPlugins(request);
        CompletableFuture<HttpResponse<String>> httpResponseFuture = httpClient.sendAsynchronousRequest(httpRequest);

        CompletableFuture<ApiResponse<ResponseType>> apiResponseFuture = responseHelper.transformResponseFuture(request, httpResponseFuture);
        apiResponseFuture.thenAccept(apiResponse -> httpApiClientPlugins.runPostRequestPlugins(request, apiResponse));
        return apiResponseFuture;
    }
}
