package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api;

import com.kiwiko.jdashboard.webapp.http.client.api.dto.ApiRequest;
import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;

import javax.servlet.http.HttpServletRequest;

public interface InternalHttpRequestValidator {

    <T extends HttpClientRequest> void authorizeOutgoingRequest(T request);
    void authorizeOutgoingRequest(ApiRequest request);

    void validateIncomingRequest(HttpServletRequest request) throws UnauthorizedInternalRequestException;
}
