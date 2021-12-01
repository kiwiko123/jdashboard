package com.kiwiko.webapp.mvc.security.authentication.api.annotations;

public enum AuthenticationLevel {
    // The user must be actively and currently authenticated.
    AUTHENTICATED,

    // Publicly accessible; no authentication is required.
    PUBLIC,

    // Only accessible by an internal Jdashboard service client. Extra validation is performed.
    INTERNAL_SERVICE
}
