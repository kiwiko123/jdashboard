package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api;

import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpRequest;

public interface InternalHttpRequestValidator {

    void authorizeOutgoingRequest(URI uri, HttpRequest.Builder httpRequestBuilder);

    void validateIncomingRequest(HttpServletRequest request) throws UnauthorizedInternalRequestException;
}
