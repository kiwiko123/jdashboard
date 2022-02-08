package com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate an endpoint method or controller class to indicate that it receives Jdashboard internal service requests via
 * {@link com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient}.
 * Jdashboard performs additional validation on internal service requests to verify their legitimacy.
 */
@AuthenticationRequired(levels = AuthenticationLevel.INTERNAL_SERVICE)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InternalServiceCheck {
}
