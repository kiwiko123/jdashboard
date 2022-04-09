package com.kiwiko.jdashboard.library.http.client.api.interfaces;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;

import java.util.concurrent.CompletableFuture;

public interface ApiClient {

    <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request)
            throws ClientException, ServerException, InterruptedException;

    <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request) throws ClientException;
}
