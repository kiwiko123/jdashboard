package com.kiwiko.webapp.mvc.controllers.api.interfaces;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@CrossOrigin(
        allowCredentials = "true",
        origins = "*") // Allow all origins here because CrossSiteRequestForgeryPreventionInterceptor will verify them
public @interface JdashboardConfigured {
}
