package com.kiwiko.jdashboard.library.http.client;

import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;

import java.util.concurrent.CompletableFuture;

public interface ApiClient {

    <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request)
            throws ClientException, ServerException, InterruptedException;

    <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request) throws ClientException;
}
