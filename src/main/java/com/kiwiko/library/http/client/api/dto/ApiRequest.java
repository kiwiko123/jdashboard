package com.kiwiko.library.http.client.api.dto;

import com.kiwiko.library.http.client.api.constants.RequestMethod;

import javax.annotation.Nullable;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Set;

/**
 * @see DefaultApiRequest
 */
public interface ApiRequest {

    /**
     * Required.
     * @return the request method for this request
     */
    RequestMethod getRequestMethod();

    RequestUrl getRequestUrl();

    /**
     * Optional.
     *
     * Return the request body to be sent with this request. This will get serialized by {@link #getRequestBodySerializer()}.
     * This only applies to POST and PUT requests.
     *
     * @return the request body
     */
    @Nullable
    Object getRequestBody();

    /**
     * Optional.
     *
     * Return the duration after which the request will time out if exceeded.
     * If not provided, a generous default value may be used.
     *
     * @return the request timeout duration
     */
    @Nullable
    Duration getRequestTimeout();

    /**
     * Required.
     *
     * Only applies if a 3xx HTTP response code is received by the server.
     *
     * @return the redirection policy for this request
     */
    HttpClient.Redirect getRedirectionPolicy();

    /**
     * Required.
     * @return the request headers to be sent with this request
     */
    Set<RequestHeader> getRequestHeaders();

    /**
     * Required.
     *
     * Return true if the target endpoint requires authentication by
     * {@link com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.AuthenticationLevel#INTERNAL_SERVICE}.
     * Extra validation will occur to verify that this is an authentic service request originating and ending in Jdashboard.
     *
     * @return true if this is an internal service request, or false otherwise
     */
    boolean isInternalServiceRequest();

    /**
     * Required for POST and PUT requests.
     *
     * @return the request body serializer
     */
    PayloadSerializer getRequestBodySerializer();

    /**
     * Required.
     *
     * @return the response payload deserializer
     */
    PayloadDeserializer getResponsePayloadDeserializer();

    @Nullable
    <ResponseType> Class<ResponseType> getResponseType();
}
