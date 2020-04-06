package com.kiwiko.mvc.requests.api;

import com.kiwiko.mvc.requests.data.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Optional;

public interface RequestContextService {

    String getRequestUri(HttpServletRequest request);

    Optional<RequestContext> getById(long requestContextId);

    Collection<RequestContext> getRequestContexts(String requestUri);

    Optional<RequestContext> getFromSession(HttpSession session, String sessionKey);

    RequestContext saveRequestContext(RequestContext context);
}
