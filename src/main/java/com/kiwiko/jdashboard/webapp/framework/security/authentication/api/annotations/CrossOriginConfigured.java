package com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations;

import com.kiwiko.jdashboard.framework.controllers.api.interfaces.JdashboardConfigured;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @deprecated prefer {@link JdashboardConfigured}
 */
@Deprecated
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@CrossOrigin(
        allowCredentials = "true",
        origins = "*") // Allow all origins here because CrossSiteRequestForgeryPreventionInterceptor will verify them
public @interface CrossOriginConfigured {
}
