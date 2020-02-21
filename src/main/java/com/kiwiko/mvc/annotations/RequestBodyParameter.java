package com.kiwiko.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Request parameter annotation that allows individual extraction of a POST request's body,
 * providing a similar interface as {@link org.springframework.web.bind.annotation.RequestParam}.
 *
 * Annotate parameters of a {@link org.springframework.web.bind.annotation.RequestMapping} endpoint.
 *
 * Uses {@link com.fasterxml.jackson.databind.ObjectMapper} to deserialize JSON strings.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBodyParameter {

    String name();

    boolean required() default true;
}