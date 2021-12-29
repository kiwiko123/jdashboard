package com.kiwiko.jdashboard.webapp.framework.security.authentication.http.internal;

import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.dto.UniversalUniqueIdentifier;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.UniqueIdentifierService;
import com.kiwiko.jdashboard.webapp.persistence.identification.unique.api.interfaces.parameters.CreateUuidParameters;
import com.kiwiko.library.http.client.api.dto.RequestHeader;
import com.kiwiko.library.lang.random.TokenGenerator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.InternalHttpRequestValidator;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.http.api.errors.UnauthorizedInternalRequestException;
import com.kiwiko.jdashboard.webapp.application.events.api.dto.ApplicationEvent;
import com.kiwiko.jdashboard.webapp.application.events.api.interfaces.ApplicationEventService;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Instant;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JdashboardInternalHttpRequestValidator implements InternalHttpRequestValidator {

    private static final Pattern VALIDATION_HEADER_FORMAT_PATTERN = Pattern.compile("^(?<urlHash>[^|]+)\\|(?<uuid>[^\\s]+)$");

    @Inject private TokenGenerator tokenGenerator;
    @Inject private JdashboardInternalRequestHasher requestHasher;
    @Inject private ApplicationEventService applicationEventService;
    @Inject private UniqueIdentifierService uniqueIdentifierService;
    @Inject private Logger logger;

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

        Matcher matcher = VALIDATION_HEADER_FORMAT_PATTERN.matcher(headerValue);
        if (!matcher.find()) {
            logger.error(String.format("Unable to match internal request header value %s", headerValue));
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }

        String receivedUrlHash = matcher.group("urlHash");
        if (!Objects.equals(urlHash, receivedUrlHash)) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }

        String uuid = matcher.group("uuid");
        UniversalUniqueIdentifier uniqueIdentifier = uniqueIdentifierService.getByUuid(uuid).orElse(null);
        if (uniqueIdentifier == null) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }

        // If the request is older than the designated time-to-live, deny the request.
        if (Instant.now().isAfter(uniqueIdentifier.getCreatedDate().plus(JdashboardInternalHttpRequestProperties.INTERNAL_REQUEST_TOKEN_TTL))) {
            throw new UnauthorizedInternalRequestException(String.format("Unauthorized internal request: %s", request.getRequestURI()));
        }
    }

    private RequestHeader makeAuthorizedOutgoingRequestHeader(URI uri) {
        String url = uri.toString();
        String urlHash = Long.toString(requestHasher.hash(url));
        String name = makeRequestHeaderName(urlHash);

        ApplicationEvent requestEvent = ApplicationEvent.newBuilder(JdashboardInternalHttpRequestProperties.EVENT_TYPE_NAME)
                .setEventKey(urlHash)
                .build();
        ApplicationEvent createdEvent = applicationEventService.create(requestEvent);
        CreateUuidParameters createUuidParameters = new CreateUuidParameters(String.format("__jdashboard_%s_application_event_id:%d", getClass().getName(), createdEvent.getId()));
        UniversalUniqueIdentifier uniqueIdentifier = uniqueIdentifierService.create(createUuidParameters);

        String headerValue = String.format("%s|%s", urlHash, uniqueIdentifier.getUuid());
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
