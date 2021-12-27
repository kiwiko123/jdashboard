package com.kiwiko.jdashboard.webapp.http.client.api.interfaces;

import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.webapp.http.client.api.exceptions.JdashboardApiClientException;

import java.util.concurrent.CompletableFuture;

public interface JdashboardApiClient {

    <ResponseType> ApiResponse<ResponseType> synchronousCall(ApiRequest request) throws
            InterruptedException, JdashboardApiClientException;

    <ResponseType> CompletableFuture<ApiResponse<ResponseType>> asynchronousCall(ApiRequest request)
            throws InterruptedException, JdashboardApiClientException;
}
