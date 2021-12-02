package com.kiwiko.jdashboard.webapp.mvc.security.authentication.http.api;

import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.jdashboard.webapp.mvc.security.authentication.http.api.errors.UnauthorizedInternalRequestException;

import javax.servlet.http.HttpServletRequest;

public interface InternalHttpRequestValidator {

    <T extends HttpClientRequest> void authorizeOutgoingRequest(T request);

    void validateIncomingRequest(HttpServletRequest request) throws UnauthorizedInternalRequestException;
}
