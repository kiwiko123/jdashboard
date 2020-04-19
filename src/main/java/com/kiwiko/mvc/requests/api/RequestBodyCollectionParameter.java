package com.kiwiko.mvc.requests.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Request parameter annotation that allows a Collection-like parameter of a request body to be
 * deserialized into a {@link java.util.Collection} or derivation of one.
 *
 * Annotate parameters of a {@link org.springframework.web.bind.annotation.RequestMapping} endpoint.
 *
 * Uses {@link com.fasterxml.jackson.databind.ObjectMapper} to deserialize JSON strings.
 *
 * Example:
 * {@code @RequestBodyCollectionParameter(name = "cars", valueType = Car.class) Set<Car> cars}
 *
 * @see RequestBodyParameter
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBodyCollectionParameter {

    /**
     * @see RequestBodyParameter#name()
     */
    String name();

    /**
     * @return the type of objects that the {@link java.util.Collection} holds.
     */
    Class<?> valueType();

    /**
     * @see RequestBodyParameter#required()
     */
    boolean required() default true;
}