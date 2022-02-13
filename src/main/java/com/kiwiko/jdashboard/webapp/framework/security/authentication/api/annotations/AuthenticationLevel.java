package com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations;

/**
 * @deprecated prefer individual check annotations
 * @see com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks.UserAuthCheck
 * @see com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks.InternalServiceCheck
 */
@Deprecated
public enum AuthenticationLevel {
    // The user must be actively and currently authenticated.
    AUTHENTICATED,

    // Publicly accessible; no authentication is required.
    PUBLIC,

    // Only accessible by an internal Jdashboard service client. Extra validation is performed.
    INTERNAL_SERVICE
}
