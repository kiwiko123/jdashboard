package com.kiwiko.library.http.client.internal;

import com.kiwiko.library.http.client.api.SynchronousHttpRequestClient;
import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.api.errors.ServerException;
import com.kiwiko.library.http.client.dto.DeleteRequest;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.dto.PostRequest;
import com.kiwiko.library.http.client.dto.PutRequest;
import com.kiwiko.webapp.mvc.json.api.JsonSerializer;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class HttpNetSynchronousRequestClient extends AbstractHttpNetRequestClient implements SynchronousHttpRequestClient {

    @Override
    public <T> HttpClientResponse<T> get(GetRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException {
        RequestConverter requestConverter = () -> httpRequestConverter.convertGetRequest(request);
        return obtainResponse(request, requestConverter, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> post(PostRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        JsonSerializer serializer = getSerializer();
        RequestConverter requestConverter = () -> httpRequestConverter.convertPostRequest(request, serializer);
        return obtainResponse(request, requestConverter, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> put(PutRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        JsonSerializer serializer = getSerializer();
        RequestConverter requestConverter = () -> httpRequestConverter.convertPutRequest(request, serializer);
        return obtainResponse(request, requestConverter, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> delete(DeleteRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        RequestConverter requestConverter = () -> httpRequestConverter.convertDeleteRequest(request);
        return obtainResponse(request, requestConverter, responseType);
    }

    private <T> HttpClientResponse<T> obtainResponse(HttpClientRequest request, RequestConverter makeHttpRequest, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException {
        Optional<HttpClientResponse<T>> cachedResponse = requestCacheHelper.getCachedResponse(request);
        if (cachedResponse.isPresent()) {
            return cachedResponse.get();
        }

        HttpClientResponse<T> response = send(request, makeHttpRequest, responseType);
        requestCacheHelper.cacheResponse(request, response);
        return response;
    }

    private <T> HttpClientResponse<T> send(HttpClientRequest request, RequestConverter makeHttpRequest, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException {
        HttpRequest httpRequest = makeHttpRequest.get();
        HttpResponse<String> httpResponse;

        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new ServerException(String.format("I/O error occurred with request %s", request.toString()), e);
        }

        return convertResponse(httpResponse, responseType);
    }
}
