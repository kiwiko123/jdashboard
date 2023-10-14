package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.library.http.client.ApiClient;
import com.kiwiko.jdashboard.library.http.client.impl.CoreHttpClient;
import com.kiwiko.jdashboard.tools.apiclient.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.HttpApiRequestContext;
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

    public <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>, ResponseType>
        ApiResponse<ResponseType>
            synchronousCall(RequestType request, RequestContextType requestContext)
                throws ClientException, ServerException, InterruptedException {
        requestHelper.validateRequest(request);

        HttpRequest httpRequest = requestHelper.makeHttpRequest(request, requestContext);
        HttpResponse<String> httpResponse = httpClient.sendSynchronousRequest(httpRequest);

        ApiResponse<ResponseType> apiResponse = responseHelper.convertHttpResponse(request, requestContext, httpResponse);
        // TODO run plugins
        return apiResponse;
    }

    @Override
    public <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request)
            throws ClientException, ServerException, InterruptedException {
        requestHelper.validateRequest(request);

        // Make the HTTP request first to handle preparation-related side effects.
        HttpRequest httpRequest = requestHelper.makeHttpRequest(request);

        Optional<ApiResponse<ResponseType>> cachedResponse = apiClientCache.getCachedResponse(request);
        if (cachedResponse.isPresent()) {
            return cachedResponse.get();
        }

        httpApiClientPlugins.runPreRequestPlugins(request);
        HttpResponse<String> httpResponse = httpClient.sendSynchronousRequest(httpRequest);

        ApiResponse<ResponseType> apiResponse = responseHelper.convertHttpResponse(request, httpResponse);
        httpApiClientPlugins.runPostRequestPlugins(request, apiResponse);

        return apiResponse;
    }

    @Override
    public <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request) throws ClientException {
        requestHelper.validateRequest(request);

        // Make the HTTP request first to handle preparation-related side effects.
        HttpRequest httpRequest = requestHelper.makeHttpRequest(request);

        Optional<ApiResponse<ResponseType>> cachedResponse = apiClientCache.getCachedResponse(request);
        if (cachedResponse.isPresent()) {
            return CompletableFuture.completedFuture(cachedResponse.get());
        }

        httpApiClientPlugins.runPreRequestPlugins(request);
        CompletableFuture<HttpResponse<String>> httpResponseFuture = httpClient.sendAsynchronousRequest(httpRequest);

        CompletableFuture<ApiResponse<ResponseType>> apiResponseFuture = responseHelper.transformResponseFuture(request, httpResponseFuture);
        apiResponseFuture.thenAccept(apiResponse -> httpApiClientPlugins.runPostRequestPlugins(request, apiResponse));
        return apiResponseFuture;
    }
}
