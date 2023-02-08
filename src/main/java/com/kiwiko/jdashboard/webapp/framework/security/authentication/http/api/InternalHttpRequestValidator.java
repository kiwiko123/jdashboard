package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api;

import com.kiwiko.jdashboard.library.http.client.ApiRequest;
import com.kiwiko.jdashboard.library.http.client.exceptions.ClientException;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Set;

public interface InternalHttpRequestValidator {

    void authorizeOutgoingRequest(URI uri, HttpRequest.Builder httpRequestBuilder, ApiRequest apiRequest) throws ClientException;

    void validateIncomingRequest(HttpServletRequest request, Set<String> authorizedServiceClientIdentifiers) throws UnauthorizedInternalRequestException;
}
