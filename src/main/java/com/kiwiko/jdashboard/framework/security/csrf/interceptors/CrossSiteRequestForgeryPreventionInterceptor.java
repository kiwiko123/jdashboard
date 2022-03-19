package com.kiwiko.jdashboard.framework.security.csrf.interceptors;

import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import com.kiwiko.jdashboard.framework.interceptors.api.interfaces.RequestInterceptor;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyConstants;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.JdashboardPropertyReader;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input.GetPropertyInput;
import com.kiwiko.jdashboard.webapp.framework.application.properties.api.interfaces.input.PropertyCacheControls;
import org.springframework.web.method.HandlerMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Web interceptor that checks requests for cross site request forgery (CSRF).
 * Allowed cross-origin URLs are defined in the Jdashboard properties file under the name {@code cross_origin_urls}.
 * If a request comes in from a cross-origin URL not listed there, it will be denied.
 */
public class CrossSiteRequestForgeryPreventionInterceptor implements RequestInterceptor {

    @Inject private JdashboardPropertyReader jdashboardPropertyFileReader;
    @Inject private Logger logger;

    @Override
    public boolean allowRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod method) throws Exception {
        PropertyCacheControls propertyCacheControls = PropertyCacheControls.indefinitely();
        GetPropertyInput getPropertyInput = new GetPropertyInput(JdashboardPropertyConstants.CROSS_ORIGIN_URLS, propertyCacheControls);
        Set<String> crossOriginUrls = jdashboardPropertyFileReader.get(getPropertyInput)
                .mappable()
                .mapToSet();
        Set<String> allowedCrossOriginUrls = crossOriginUrls.stream()
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
