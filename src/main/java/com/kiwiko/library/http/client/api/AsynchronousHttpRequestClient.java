package com.kiwiko.library.http.client.api;

import com.kiwiko.library.http.client.api.errors.ClientException;
import com.kiwiko.library.http.client.dto.DeleteRequest;
import com.kiwiko.library.http.client.dto.GetRequest;
import com.kiwiko.library.http.client.dto.HttpClientResponse;
import com.kiwiko.library.http.client.dto.PostRequest;
import com.kiwiko.library.http.client.dto.PutRequest;

import java.util.concurrent.CompletableFuture;

public interface AsynchronousHttpRequestClient {

    <T> CompletableFuture<HttpClientResponse<T>> get(GetRequest request, Class<T> responseType) throws ClientException;

    <T> CompletableFuture<HttpClientResponse<T>> post(PostRequest request, Class<T> responseType) throws ClientException;

    <T> CompletableFuture<HttpClientResponse<T>> put(PutRequest request, Class<T> responseType) throws ClientException;

    <T> CompletableFuture<HttpClientResponse<T>> delete(DeleteRequest request, Class<T> responseType) throws ClientException;
}
