package com.kiwiko.webapp.mvc.security.authentication.api.annotations;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@CrossOrigin(
        allowCredentials = "true",
        origins = "*") // Allow all origins here because CrossSiteRequestForgeryPreventionInterceptor will verify them
public @interface CrossOriginConfigured {
}
