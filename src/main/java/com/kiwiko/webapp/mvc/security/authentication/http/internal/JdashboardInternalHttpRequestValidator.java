package com.kiwiko.webapp.mvc.security.authentication.http.internal;

import com.kiwiko.library.caching.api.ObjectCache;
import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.library.http.client.dto.RequestHeader;
import com.kiwiko.library.lang.random.TokenGenerator;
import com.kiwiko.webapp.mvc.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.webapp.mvc.security.authentication.http.api.errors.UnauthorizedInternalRequestException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class JdashboardInternalHttpRequestValidator implements InternalHttpRequestValidator {

    @Inject private ObjectCache objectCache;
    @Inject private TokenGenerator tokenGenerator;
    @Inject private JdashboardInternalRequestHasher requestHasher;

    @Override
    public <T extends HttpClientRequest> void authorizeOutgoingRequest(T request) {
        RequestHeader internalRequestHeader = makeAuthorizedOutgoingRequestHeader(request);
        request.addRequestHeader(internalRequestHeader);
    }

    @Override
    public void validateIncomingRequest(HttpServletRequest request) throws UnauthorizedInternalRequestException {
        String url = getFullUrl(request);
        String urlHash = Long.toString(requestHasher.hash(url));
        String cacheKey = makeCacheKey(urlHash);
        String token = objectCache.<String>get(cacheKey).orElse(null);

        if (token == null) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }

        String headerName = makeRequestHeaderName(urlHash);
        String actualTokenValue = request.getHeader(headerName);

        if (!Objects.equals(token, actualTokenValue)) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }

        objectCache.invalidate(cacheKey);
    }

    private <T extends HttpClientRequest> RequestHeader makeAuthorizedOutgoingRequestHeader(T request) {
        String urlHash = Long.toString(requestHasher.hash(request.getUrl()));
        String name = makeRequestHeaderName(urlHash);
        String valueToken = tokenGenerator.generateToken(JdashboardInternalHttpRequestProperties.HEADER_VALUE_TOKEN_LENGTH);
        String cacheKey = makeCacheKey(urlHash);
        objectCache.cache(cacheKey, valueToken, JdashboardInternalHttpRequestProperties.INTERNAL_REQUEST_TOKEN_TTL);

        return new RequestHeader(name, valueToken);
    }

    private String getFullUrl(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString != null) {
            builder.append('?').append(queryString);
        }

        return builder.toString();
    }

    private String makeRequestHeaderName(String url) {
        return String.format("%s_%s", JdashboardInternalHttpRequestProperties.INTERNAL_REQUEST_HEADER_PREFIX, url);
    }

    private String makeCacheKey(String value) {
        return String.format("%s_%s", JdashboardInternalHttpRequestProperties.INTERNAL_REQUEST_HEADER_PREFIX, value);
    }
}
