package com.kiwiko.jdashboard.tools.apiclient;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.library.http.client.exceptions.ServerException;

import java.util.concurrent.CompletableFuture;

public interface JdashboardApiClient {

    <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>, ResponseType>
        ClientResponse<ResponseType>
            synchronousCall(RequestType request, RequestContextType requestContext)
                throws ClientException, ServerException, InterruptedException;

    <ResponseType> ClientResponse<ResponseType> synchronousCall(ApiRequest apiRequest)
            throws ClientException, ServerException, InterruptedException;

    <RequestType extends HttpApiRequest, RequestContextType extends HttpApiRequestContext<RequestType>, ResponseType>
        ClientResponse<ResponseType>
            silentSynchronousCall(RequestType request, RequestContextType requestContext)
                throws ClientException, ServerException, InterruptedException;

    /**
     * Like {@link #synchronousCall(ApiRequest)}, but does not throw checked exceptions.
     * If one of the checked exceptions occurs, it will not be thrown;
     * the {@link ClientResponse} will contain an unsuccessful
     * {@link ResponseStatus} that reflects the exception.
     *
     * @see #synchronousCall(ApiRequest)
     */
    <ResponseType> ClientResponse<ResponseType> silentSynchronousCall(JdashboardApiRequest apiRequest);

    <ResponseType> CompletableFuture<ClientResponse<ResponseType>> asynchronousCall(ApiRequest apiRequest) throws ClientException;

    /**
     * Like {@link #asynchronousCall(ApiRequest)}, but does not throw checked exceptions.
     * If one of the checked exceptions occurs, it will not be thrown;
     * the {@link ClientResponse} will contain an unsuccessful
     * {@link ResponseStatus} that reflects the exception.
     *
     * @see #asynchronousCall(ApiRequest)
     */
    <ResponseType> CompletableFuture<ClientResponse<ResponseType>> silentAsynchronousCall(JdashboardApiRequest apiRequest);
}
