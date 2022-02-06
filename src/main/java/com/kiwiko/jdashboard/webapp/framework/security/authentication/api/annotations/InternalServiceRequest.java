package com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@AuthenticationRequired(levels = AuthenticationLevel.INTERNAL_SERVICE)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InternalServiceRequest {
}
