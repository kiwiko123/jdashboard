package com.kiwiko.memory.performance.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

/**
 * Annotate a method with this to prevent it from being invoked within a given span of time.
 * For example, if the parameters indicate 5 seconds, then the annotated method cannot be invoked more than once per five seconds.
 * Attempts to invoke a throttled method will result in no action being taken.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Throttle {

    int maxWait();

    ChronoUnit timeUnit();

    boolean throwException() default false;
}
