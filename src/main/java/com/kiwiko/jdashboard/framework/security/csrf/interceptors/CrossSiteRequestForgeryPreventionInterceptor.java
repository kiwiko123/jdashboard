package com.kiwiko.jdashboard.framework.security.csrf.interceptors;

import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Web interceptor that checks requests for cross site request forgery (CSRF).
 * Allowed cross-origin URLs are defined in the Jdashboard properties file under the name {@code cross_origin_urls}.
 * If a request comes in from a cross-origin URL not listed there, it will be denied.
 */
public class CrossSiteRequestForgeryPreventionInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrossSiteRequestForgeryPreventionInterceptor.class);

    private final Set<String> allowedCrossOriginUrls;

    @Inject
    public CrossSiteRequestForgeryPreventionInterceptor(
            @Value("${jdashboard.framework.security.csrf.allowed-cross-origin-urls}") String[] allowedCrossOriginUrls) {
        this.allowedCrossOriginUrls = Arrays.stream(allowedCrossOriginUrls)
                .map(this::normalizeUrl)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        String requestUri = normalizeUrl(buildUrl(request));
        if (!allowedCrossOriginUrls.contains(requestUri)) {
            LOGGER.warn(String.format("%s is not a permitted cross-origin URL; denying request (%s)", requestUri, request.getRequestURL().toString()));
            return false;
        }

        return true;
    }

    private String normalizeUrl(String url) {
        return url.toLowerCase();
    }

    private String buildUrl(HttpServletRequest request) {
        return new StringBuilder()
                .append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(':').append(request.getServerPort()) // TODO verify if port is always necessary
                .toString();
    }
}
