package com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a {@link org.springframework.context.annotation.Configuration} class with this
 * to specify its access scope by other configurations.
 *
 * For example, if a configuration has scope PRIVATE, then all of the {@link org.springframework.context.annotation.Bean}s
 * that it wires can only be dependency injected within that Java package.
 *
 * @see ConfigurationScopeLevel
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigurationScope {
    ConfigurationScopeLevel value();
}
