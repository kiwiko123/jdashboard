package com.kiwiko.webapp.mvc.security.csrf.interceptors;

import com.kiwiko.library.files.properties.readers.api.dto.Property;
import com.kiwiko.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.webapp.middleware.interceptors.api.interfaces.EndpointInterceptor;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyConstants;
import com.kiwiko.webapp.mvc.application.properties.api.interfaces.JdashboardPropertyReader;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CrossSiteRequestForgeryPreventionInterceptor implements EndpointInterceptor {

    @Inject private JdashboardPropertyReader jdashboardPropertyFileReader;
    @Inject private Logger logger;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        Property<List<String>> crossOriginUrls = jdashboardPropertyFileReader.getList(JdashboardPropertyConstants.CROSS_ORIGIN_URLS);

        if (crossOriginUrls == null) {
            logger.error(String.format("No cross origin URL property found; denying request %s", request.getRequestURL().toString()));
            return false;
        }

        Set<String> allowedCrossOriginUrls = crossOriginUrls.getValue().stream()
                .map(this::normalizeUrl)
                .collect(Collectors.toSet());
        String requestUri = normalizeUrl(buildUrl(request));
        if (!allowedCrossOriginUrls.contains(requestUri)) {
            logger.warn(String.format("%s is not a permitted cross-origin URL; denying request (%s)", requestUri, request.getRequestURL().toString()));
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
