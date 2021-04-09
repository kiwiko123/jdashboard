package com.kiwiko.webapp.http.client.api;

import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.api.errors.ServerException;
import com.kiwiko.library.http.client.dto.DeleteRequest;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.dto.PostRequest;
import com.kiwiko.library.http.client.dto.PutRequest;

import java.util.concurrent.CompletableFuture;

public interface HttpClient {

    <T> HttpClientResponse<T> syncGet(GetRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException;

    <T> HttpClientResponse<T> syncPost(PostRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException;

    <T> HttpClientResponse<T> syncPut(PutRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException;

    <T> HttpClientResponse<T> syncDelete(DeleteRequest request, Class<T> responseType)
            throws ClientException, InterruptedException, ServerException;

    <T> CompletableFuture<HttpClientResponse<T>> asyncGet(GetRequest request, Class<T> responseType) throws ClientException;

    <T> CompletableFuture<HttpClientResponse<T>> asyncPost(PostRequest request, Class<T> responseType) throws ClientException;

    <T> CompletableFuture<HttpClientResponse<T>> asyncPut(PutRequest request, Class<T> responseType) throws ClientException;

    <T> CompletableFuture<HttpClientResponse<T>> asyncDelete(DeleteRequest request, Class<T> responseType) throws ClientException;
}
