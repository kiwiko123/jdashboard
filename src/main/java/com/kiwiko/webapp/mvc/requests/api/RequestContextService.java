package com.kiwiko.webapp.mvc.requests.api;

import com.kiwiko.webapp.mvc.requests.data.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface RequestContextService {

    String getRequestUri(HttpServletRequest request);

    Optional<RequestContext> getById(long requestContextId);

    Optional<RequestContext> getFromSession(HttpSession session, String sessionKey);

    RequestContext saveRequestContext(RequestContext context);
}
