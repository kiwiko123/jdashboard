package com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations;

public enum AuthenticationLevel {
    // The user must be actively and currently authenticated.
    AUTHENTICATED,

    // Publicly accessible; no authentication is required.
    PUBLIC,

    // Only accessible by an internal Jdashboard service client. Extra validation is performed.
    // Deprecated -- prefer @InternalServiceRequest.
    @Deprecated INTERNAL_SERVICE
}
