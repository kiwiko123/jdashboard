package com.kiwiko.jdashboard.framework.controllers.api.annotations.checks;

import com.kiwiko.jdashboard.tools.apiclient.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.interceptors.LockedApiInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate an endpoint method or controller class to indicate that it receives service requests via {@link JdashboardApiClient}.
 *
 * Jdashboard performs additional validation on internal service requests to verify their legitimacy.
 *
 * @see LockedApiInterceptor
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockedApi {

    /**
     * @return the service client identifiers allowed for this endpoint/controller
     */
    String[] value() default {};
}
