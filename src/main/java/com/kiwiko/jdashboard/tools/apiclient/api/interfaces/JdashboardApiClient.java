package com.kiwiko.jdashboard.tools.apiclient.api.interfaces;

import com.kiwiko.jdashboard.library.http.client.api.dto.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.api.exceptions.ServerException;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.JdashboardApiRequest;

import java.util.concurrent.CompletableFuture;

public interface JdashboardApiClient {

    <ResponseType> ClientResponse<ResponseType> synchronousCall(ApiRequest apiRequest)
            throws ClientException, ServerException, InterruptedException;

    /**
     * Like {@link #synchronousCall(ApiRequest)}, but does not throw checked exceptions.
     * If one of the checked exceptions occurs, it will not be thrown;
     * the {@link ClientResponse} will contain an unsuccessful
     * {@link com.kiwiko.jdashboard.tools.apiclient.api.dto.ResponseStatus} that reflects the exception.
     *
     * @see #synchronousCall(ApiRequest)
     */
    <ResponseType> ClientResponse<ResponseType> silencedSynchronousCall(JdashboardApiRequest apiRequest);

    <ResponseType> CompletableFuture<ClientResponse<ResponseType>> asynchronousCall(ApiRequest apiRequest)
            throws ServerException, ClientException, InterruptedException;

    /**
     * Like {@link #asynchronousCall(ApiRequest)}, but does not throw checked exceptions.
     * If one of the checked exceptions occurs, it will not be thrown;
     * the {@link ClientResponse} will contain an unsuccessful
     * {@link com.kiwiko.jdashboard.tools.apiclient.api.dto.ResponseStatus} that reflects the exception.
     *
     * @see #asynchronousCall(ApiRequest)
     */
    <ResponseType> CompletableFuture<ClientResponse<ResponseType>> silencedAsynchronousCall(JdashboardApiRequest apiRequest);
}
