package com.kiwiko.jdashboard.tools.apiclient.api.interfaces;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;

import java.util.concurrent.CompletableFuture;

public interface JdashboardApiClient {

    <ResponseType> ClientResponse<ResponseType> synchronousCall(ApiRequest apiRequest)
            throws ClientException, ServerException, InterruptedException;

    <ResponseType> CompletableFuture<ClientResponse<ResponseType>> asynchronousCall(ApiRequest apiRequest)
            throws ServerException, ClientException, InterruptedException;
}
