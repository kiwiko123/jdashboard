package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import com.kiwiko.library.http.client.dto.HttpClientRequest;
import com.kiwiko.library.http.client.dto.RequestHeader;
import com.kiwiko.library.lang.random.TokenGenerator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;
import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Instant;
import java.util.Objects;

public class JdashboardInternalHttpRequestValidator implements InternalHttpRequestValidator {

    @Inject private TokenGenerator tokenGenerator;
    @Inject private JdashboardInternalRequestHasher requestHasher;
    @Inject private ApplicationEventService applicationEventService;

    @Override
    public <T extends HttpClientRequest> void authorizeOutgoingRequest(T request) {
        RequestHeader internalRequestHeader = makeAuthorizedOutgoingRequestHeader(request);
        request.addRequestHeader(internalRequestHeader);
    }

    @Override
    public void authorizeOutgoingRequest(URI uri, HttpRequest.Builder httpRequestBuilder) {
        RequestHeader internalRequestHeader = makeAuthorizedOutgoingRequestHeader(uri);
        httpRequestBuilder.header(internalRequestHeader.getName(), internalRequestHeader.getValue());
    }

    @Override
    public void validateIncomingRequest(HttpServletRequest request) throws UnauthorizedInternalRequestException {
        String url = getFullUrl(request);
        String urlHash = Long.toString(requestHasher.hash(url));
        String headerName = makeRequestHeaderName(urlHash);
        String headerValue = request.getHeader(headerName);

        if (headerValue == null) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }

        int splitIndex = headerValue.lastIndexOf('-');
        String receivedUrlHash = headerValue.substring(0, splitIndex);
        if (!Objects.equals(urlHash, receivedUrlHash)) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }

        long applicationEventId = Long.parseLong(headerValue.substring(splitIndex + 1));
        ApplicationEvent requestEvent = applicationEventService.get(applicationEventId).orElse(null);
        if (requestEvent == null) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }

        // If the request is older than the designated time-to-live, deny the request.
        if (Instant.now().isAfter(requestEvent.getCreatedDate().plus(JdashboardInternalHttpRequestProperties.INTERNAL_REQUEST_TOKEN_TTL))) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }
    }

    private <T extends HttpClientRequest> RequestHeader makeAuthorizedOutgoingRequestHeader(T request) {
        String urlHash = Long.toString(requestHasher.hash(request.getUrl()));
        String name = makeRequestHeaderName(urlHash);

        ApplicationEvent requestEvent = ApplicationEvent.newBuilder(JdashboardInternalHttpRequestProperties.EVENT_TYPE_NAME)
                .setEventKey(urlHash)
                .build();
        ApplicationEvent createdEvent = applicationEventService.create(requestEvent);

        String headerValue = String.format("%s-%d", urlHash, createdEvent.getId());
        return new RequestHeader(name, headerValue);
    }

    private RequestHeader makeAuthorizedOutgoingRequestHeader(URI uri) {
        String url = uri.toString();
        String urlHash = Long.toString(requestHasher.hash(url));
        String name = makeRequestHeaderName(urlHash);

        ApplicationEvent requestEvent = ApplicationEvent.newBuilder(JdashboardInternalHttpRequestProperties.EVENT_TYPE_NAME)
                .setEventKey(urlHash)
                .build();
        ApplicationEvent createdEvent = applicationEventService.create(requestEvent);

        String headerValue = String.format("%s-%d", urlHash, createdEvent.getId());
        return new RequestHeader(name, headerValue);
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
}
