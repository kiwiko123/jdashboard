package com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Retrieve the {@link com.kiwiko.jdashboard.webapp.users.data.User} issuing the current request in a web endpoint.
 * Throws {@link com.kiwiko.jdashboard.webapp.framework.security.authentication.api.errors.AuthenticatedUserException} if a user is not logged in.
 * Can be paired with {@link AuthenticationRequired} to safely retrieve the current user.
 *
 * @see com.kiwiko.jdashboard.webapp.framework.security.authentication.internal.resolvers.AuthenticatedUserArgumentResolver
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedUser {

    /**
     * If true, throw {@link com.kiwiko.jdashboard.webapp.framework.security.authentication.api.errors.AuthenticatedUserException} if the user is not authenticated.
     * If false, resolve the argument to {@code null}.
     *
     * @return true to require an authenticated user, or false to resolve to null
     */
    boolean required() default true;
}
