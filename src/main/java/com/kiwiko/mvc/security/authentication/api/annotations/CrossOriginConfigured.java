package com.kiwiko.mvc.security.authentication.api.annotations;

import com.kiwiko.mvc.security.environments.data.EnvironmentProperties;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@CrossOrigin(
        origins = {EnvironmentProperties.CROSS_ORIGIN_DEV_URL, EnvironmentProperties.CROSS_ORIGIN_DEV_URL_ALT},
        allowCredentials = "true")
//@CrossOrigin(origins = EnvironmentProperties.CROSS_ORIGIN_URL, allowCredentials = "true")
public @interface CrossOriginConfigured {
}
