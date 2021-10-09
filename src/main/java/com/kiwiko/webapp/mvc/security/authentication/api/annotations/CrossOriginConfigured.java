package com.kiwiko.webapp.mvc.security.authentication.api.annotations;

import com.kiwiko.webapp.mvc.controllers.api.interfaces.JdashboardConfigured;
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
        originPatterns = "*") // Allow all origins here because CrossSiteRequestForgeryPreventionInterceptor will verify them
public @interface CrossOriginConfigured {
}
