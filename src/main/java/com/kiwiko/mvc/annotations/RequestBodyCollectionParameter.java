package com.kiwiko.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Request parameter annotation that allows a Collection-like parameter of a request body to be
 * deserialized into a specific class.
 *
 * Annotate parameters of a {@link org.springframework.web.bind.annotation.RequestMapping} endpoint.
 *
 * Uses {@link com.fasterxml.jackson.databind.ObjectMapper} to deserialize JSON strings.
 *
 * Example:
 * {@code @RequestBodyCollectionParameter(name = "cars", valueType = Car.class) Set<Car> cars}
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBodyCollectionParameter {

    /**
     * @return the name/path/key of the object in the request's body.
     */
    String name();

    /**
     * @return the type of objects that the {@link java.util.Collection} holds.
     */
    Class<?> valueType();

    /**
     * If the parameter is required, but it's not found in the request body,
     * throws a {@link com.kiwiko.mvc.requests.api.RequestError}.
     *
     * @return true if the parameter is required, or false if not.
     */
    boolean required() default true;
}