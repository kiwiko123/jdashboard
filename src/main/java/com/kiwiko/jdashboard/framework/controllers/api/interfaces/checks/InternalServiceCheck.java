package com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks;

import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate an endpoint method or controller class to indicate that it receives Jdashboard internal service requests via
 * {@link JdashboardApiClient}.
 * Jdashboard performs additional validation on internal service requests to verify their legitimacy.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InternalServiceCheck {
}
