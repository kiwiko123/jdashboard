package com.kiwiko.library.http.client.internal;

import com.kiwiko.library.http.client.api.AsynchronousHttpRequestClient;
import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.dto.DeleteRequest;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.dto.PostRequest;
import com.kiwiko.library.http.client.dto.PutRequest;
import com.kiwiko.webapp.mvc.json.api.JsonSerializer;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class HttpNetAsynchronousRequestClient extends AbstractHttpNetRequestClient implements AsynchronousHttpRequestClient {

    @Override
    public <T> CompletableFuture<HttpClientResponse<T>> get(
            GetRequest request,
            Class<T> responseType)
            throws ClientException {
        RequestConverter makeHttpRequest = () -> httpRequestConverter.convertGetRequest(request);
        return obtainResponse(request, makeHttpRequest, responseType);
    }

    @Override
    public <T> CompletableFuture<HttpClientResponse<T>> post(PostRequest request, Class<T> responseType) throws ClientException {
        JsonSerializer serializer = getSerializer();
        RequestConverter makeHttpRequest = () -> httpRequestConverter.convertPostRequest(request, serializer);
        return obtainResponse(request, makeHttpRequest, responseType);
    }

    @Override
    public <T> CompletableFuture<HttpClientResponse<T>> put(PutRequest request, Class<T> responseType) throws ClientException {
        JsonSerializer serializer = getSerializer();
        RequestConverter makeHttpRequest = () -> httpRequestConverter.convertPutRequest(request, serializer);
        return obtainResponse(request, makeHttpRequest, responseType);
    }

    @Override
    public <T> CompletableFuture<HttpClientResponse<T>> delete(DeleteRequest request, Class<T> responseType) throws ClientException {
        RequestConverter makeHttpRequest = () -> httpRequestConverter.convertDeleteRequest(request);
        return obtainResponse(request, makeHttpRequest, responseType);
    }

    private <T> CompletableFuture<HttpClientResponse<T>> obtainResponse(HttpClientRequest request, RequestConverter makeHttpRequest, Class<T> responseType)
            throws ClientException {
        Optional<HttpClientResponse<T>> cachedResponse = requestCacheHelper.getCachedResponse(request);
        if (cachedResponse.isPresent()) {
            return CompletableFuture.completedFuture(cachedResponse.get());
        }

        return send(makeHttpRequest, responseType)
                .whenComplete((response, exception) -> requestCacheHelper.cacheResponse(request, response));
    }

    private <T> CompletableFuture<HttpClientResponse<T>> send(RequestConverter makeHttpRequest, Class<T> responseType)
            throws ClientException {
        HttpRequest httpRequest = makeHttpRequest.get();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(httpResponse -> convertResponse(httpResponse, responseType));
    }
}
