package com.kiwiko.jdashboard.library.http.client.api.interfaces;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.JdashboardApiClientException;

import java.util.concurrent.CompletableFuture;

public interface ApiClient {

    <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request) throws
            InterruptedException, JdashboardApiClientException;

    <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request)
            throws InterruptedException, JdashboardApiClientException;
}
