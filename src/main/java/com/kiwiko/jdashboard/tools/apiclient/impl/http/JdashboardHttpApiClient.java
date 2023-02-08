package com.kiwiko.jdashboard.tools.apiclient.impl.http;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.ApiResponse;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ResponseStatus;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;
import org.springframework.http.HttpStatus;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class JdashboardHttpApiClient implements JdashboardApiClient {

    @Inject private HttpApiClient httpApiClient;
    @Inject private Logger logger;

    @Override
    public <ResponseType> ClientResponse<ResponseType> synchronousCall(ApiRequest apiRequest)
            throws ClientException, ServerException, InterruptedException {
        ApiResponse<ResponseType> apiResponse = httpApiClient.synchronousCall(apiRequest);
        return toClientResponse(apiResponse);
    }

    @Override
    public <ResponseType> ClientResponse<ResponseType> silentSynchronousCall(JdashboardApiRequest apiRequest) {
        ClientResponse<ResponseType> response;

        try {
            response = synchronousCall(apiRequest);
        } catch (ClientException | ServerException | InterruptedException e) {
            response = getErrorResponse(apiRequest, e);
        }

        return response;
    }

    @Override
    public <ResponseType> CompletableFuture<ClientResponse<ResponseType>> asynchronousCall(ApiRequest apiRequest)
            throws ClientException {
        CompletableFuture<ApiResponse<ResponseType>> apiResponseFuture = httpApiClient.asynchronousCall(apiRequest);
        return apiResponseFuture.thenApply(this::toClientResponse);
    }

    @Override
    public <ResponseType> CompletableFuture<ClientResponse<ResponseType>> silentAsynchronousCall(JdashboardApiRequest apiRequest) {
        try {
            return asynchronousCall(apiRequest);
        } catch (ClientException e) {
            return CompletableFuture.completedFuture(getErrorResponse(apiRequest, e));
        }
    }

    private <ResponseType> ClientResponse<ResponseType> toClientResponse(ApiResponse<ResponseType> apiResponse) {
        HttpStatus httpStatus = HttpStatus.valueOf(apiResponse.getHttpStatusCode());
        boolean isSuccessful = !httpStatus.isError();
        String statusCode = Integer.toString(httpStatus.value());
        String errorMessage = isSuccessful ? null : statusCode;
        ResponseStatus responseStatus = new ResponseStatus(isSuccessful, statusCode, errorMessage);

        return new ClientResponse<>(apiResponse.getPayload(), responseStatus);
    }

    private <ResponseType> ClientResponse<ResponseType> getErrorResponse(ApiRequest apiRequest, Throwable throwable) {
        logger.error(String.format("Error issuing silenced request %s", apiRequest), throwable);
        String stackTraceMessage = Arrays.stream(throwable.getStackTrace())
                .limit(10)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
        ResponseStatus responseStatus = ResponseStatus.fromMessage(stackTraceMessage);
        return new ClientResponse<>(null, responseStatus);
    }
}
