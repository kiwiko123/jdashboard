package com.kiwiko.webapp.http.client.internal;

import com.kiwiko.library.http.client.api.AsynchronousHttpRequestClient;
import com.kiwiko.library.http.client.api.SynchronousHttpRequestClient;
import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.api.errors.ServerException;
import com.kiwiko.library.http.client.dto.DeleteRequest;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.dto.PostRequest;
import com.kiwiko.library.http.client.dto.PutRequest;
import com.kiwiko.webapp.http.client.api.HttpClient;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class JdashboardHttpClient implements HttpClient {

    @Inject private HttpClientHelper httpClientHelper;
    @Inject private SynchronousHttpRequestClient synchronousHttpRequestClient;
    @Inject private AsynchronousHttpRequestClient asynchronousHttpRequestClient;

    @Override
    public <T> HttpClientResponse<T> syncGet(GetRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        httpClientHelper.setRequestData(request);
        return synchronousHttpRequestClient.get(request, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> syncPost(PostRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        httpClientHelper.setRequestData(request);
        return synchronousHttpRequestClient.post(request, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> syncPut(PutRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        httpClientHelper.setRequestData(request);
        return synchronousHttpRequestClient.put(request, responseType);
    }

    @Override
    public <T> HttpClientResponse<T> syncDelete(DeleteRequest request, Class<T> responseType) throws ClientException, InterruptedException, ServerException {
        httpClientHelper.setRequestData(request);
        return synchronousHttpRequestClient.delete(request, responseType);
    }

    @Override
    public <T> CompletableFuture<HttpClientResponse<T>> asyncGet(GetRequest request, Class<T> responseType) throws ClientException {
        httpClientHelper.setRequestData(request);
        return asynchronousHttpRequestClient.get(request, responseType);
    }

    @Override
    public <T> CompletableFuture<HttpClientResponse<T>> asyncPost(PostRequest request, Class<T> responseType) throws ClientException {
        httpClientHelper.setRequestData(request);
        return asynchronousHttpRequestClient.post(request, responseType);
    }

    @Override
    public <T> CompletableFuture<HttpClientResponse<T>> asyncPut(PutRequest request, Class<T> responseType) throws ClientException {
        httpClientHelper.setRequestData(request);
        return asynchronousHttpRequestClient.put(request, responseType);
    }

    @Override
    public <T> CompletableFuture<HttpClientResponse<T>> asyncDelete(DeleteRequest request, Class<T> responseType) throws ClientException {
        httpClientHelper.setRequestData(request);
        return asynchronousHttpRequestClient.delete(request, responseType);
    }
}
