package com.kiwiko.webapp.mvc.security.authentication.api.annotations;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(
        allowCredentials = "true",
        origins = "*") // Allow all origins here because CrossSiteRequestForgeryPreventionInterceptor will verify them
public @interface CrossOriginConfigured {
}
