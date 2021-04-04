package com.kiwiko.library.http.client.internal;

import com.google.gson.Gson;
import com.kiwiko.library.http.client.api.SynchronousHttpRequestClient;
import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.api.errors.ServerException;
import com.kiwiko.library.http.client.dto.DeleteRequest;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.dto.PostRequest;
import com.kiwiko.library.http.client.dto.PutRequest;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpNetSynchronousRequestClient extends AbstractHttpNetRequestClient implements SynchronousHttpRequestClient {

    @Override
    public <T> HttpClientResponse<T> get(GetRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException {
        RequestConverter requestConverter = () -> httpRequestConverter.convertGetRequest(request);
        return send(request, requestConverter, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> post(PostRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        Gson serializer = getSerializer();
        RequestConverter requestConverter = () -> httpRequestConverter.convertPostRequest(request, serializer);
        return send(request, requestConverter, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> put(PutRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        Gson serializer = getSerializer();
        RequestConverter requestConverter = () -> httpRequestConverter.convertPutRequest(request, serializer);
        return send(request, requestConverter, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> delete(DeleteRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        RequestConverter requestConverter = () -> httpRequestConverter.convertDeleteRequest(request);
        return send(request, requestConverter, responseType);
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
