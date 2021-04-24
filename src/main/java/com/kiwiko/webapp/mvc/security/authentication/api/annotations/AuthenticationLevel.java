package com.kiwiko.webapp.mvc.security.authentication.api.annotations;

public enum AuthenticationLevel {
    AUTHENTICATED,     // The user must be actively and currently authenticated.
    NONE               // No authentication is required.
}
