package com.kiwiko.jdashboard.webapp.mvc.requests.api;

import com.kiwiko.jdashboard.webapp.mvc.requests.data.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface CurrentRequestService {

    /**
     * Retrieve information about the current HTTP request in progress.
     *
     * @return the current HTTP request's context, if available
     */
    Optional<RequestContext> getCurrentRequestContext();

    /**
     * Retrieve the current HTTP servlet request in progress.
     * When possible, prefer {@link #getCurrentRequestContext()}.
     *
     * @return the current HTTP servlet request in progress, if available
     */
    Optional<HttpServletRequest> getCurrentHttpServletRequest();
}
