package com.kiwiko.jdashboard.webapp.permissions.framework.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a controller or endpoint method with this to perform a permission check restriction.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {

    /**
     * @return the permission names that are required to access the annotated controller or method.
     * @see com.kiwiko.jdashboard.webapp.permissions.core.api.interfaces.PermissionNames
     */
    String[] value() default {};
}