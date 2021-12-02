package com.kiwiko.jdashboard.webapp.mvc.configuration.api.interfaces.annotations;

import com.kiwiko.jdashboard.webapp.monitoring.logging.LoggingConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An extension of {@link ConfiguredBy} that automatically includes default configurations for core machinery like
 * logging.
 */
@Inherited
@ConfiguredBy(LoggingConfiguration.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoConfiguredBy {

    Class<?>[] value();
}
