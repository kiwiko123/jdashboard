package com.kiwiko.jdashboard.webapp.framework.requests.api;

import com.kiwiko.jdashboard.webapp.framework.requests.data.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface RequestContextService {

    String getRequestUri(HttpServletRequest request);

    Optional<RequestContext> getById(long requestContextId);

    Optional<RequestContext> getFromSession(HttpSession session, String sessionKey);

    RequestContext create(RequestContext requestContext);

    RequestContext merge(RequestContext requestContext);
}
