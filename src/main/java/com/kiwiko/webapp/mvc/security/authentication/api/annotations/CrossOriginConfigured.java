package com.kiwiko.webapp.mvc.security.authentication.api.annotations;

import com.kiwiko.webapp.mvc.security.environments.data.EnvironmentProperties;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL, allowCredentials = "true")
public @interface CrossOriginConfigured {
}
