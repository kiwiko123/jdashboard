package com.kiwiko.jdashboard.webapp.framework.controllers.api.interfaces.checks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a controller or endpoint method with this to perform a permission check restriction.
 *
 * A user permission check inherently requires an authenticated user; it is unnecessary to additionally mark an
 * endpoint/controller with {@link UserAuthCheck}.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPermissionCheck {

    /**
     * @return the permission names that are required to access the annotated controller or method.
     * @see com.kiwiko.jdashboard.webapp.permissions.api.interfaces.PermissionNames
     */
    String[] value() default {};
}
