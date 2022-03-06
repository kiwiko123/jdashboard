package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.dto.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ResponseStatus;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;
import org.springframework.http.HttpStatus;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public class JdashboardHttpApiClient implements JdashboardApiClient {

    @Inject private HttpApiClient httpApiClient;

    @Override
    public <ResponseType> ClientResponse<ResponseType> synchronousCall(ApiRequest apiRequest)
            throws ClientException, ServerException, InterruptedException {
        ApiResponse<ResponseType> apiResponse = httpApiClient.synchronousCall(apiRequest);
        return toClientResponse(apiResponse);
    }

    @Override
    public <ResponseType> CompletableFuture<ClientResponse<ResponseType>> asynchronousCall(ApiRequest apiRequest)
            throws ServerException, ClientException, InterruptedException {
        CompletableFuture<ApiResponse<ResponseType>> apiResponseFuture = httpApiClient.asynchronousCall(apiRequest);
        return apiResponseFuture.thenApply(this::toClientResponse);
    }

    private <ResponseType> ClientResponse<ResponseType> toClientResponse(ApiResponse<ResponseType> apiResponse) {
        HttpStatus httpStatus = HttpStatus.valueOf(apiResponse.getHttpStatusCode());
        boolean isSuccessful = !httpStatus.isError();
        String statusCode = Integer.toString(httpStatus.value());
        String errorMessage = isSuccessful ? null : statusCode;
        ResponseStatus responseStatus = new ResponseStatus(isSuccessful, statusCode, errorMessage);

        return new ClientResponse<>(apiResponse.getPayload(), responseStatus);
    }
}
