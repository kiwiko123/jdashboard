package com.kiwiko.jdashboard.framework.controllers.api.interfaces.checks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate an endpoint method or controller class to require that the user issuing the request is currently and
 * actively authenticated in Jdashboard.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAuthCheck {
}
