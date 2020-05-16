package com.kiwiko.webapp.mvc.requests.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Request parameter annotation that allows individual extraction of keys from a request's body,
 * providing a similar interface as {@link org.springframework.web.bind.annotation.RequestParam}.
 * This provides an alternative to {@link org.springframework.web.bind.annotation.RequestBody},
 * which requires the definition of a Java class for every new payload shape.
 *
 * Annotate parameters of a {@link org.springframework.web.bind.annotation.RequestMapping} endpoint.
 *
 * Supports standard Java types and any corresponding primitive types: {@link Integer}, {@link Long}, {@link Boolean}, {@link String}.
 * To deserialize JavaScript arrays into {@link java.util.Collection}s, use {@link RequestBodyCollectionParameter}.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBodyParameter {

    /**
     * @return the name of the key to extract from the request body
     */
    String name();

    /**
     * Determines if the value associated with {@link #name()} must be present in the request.
     * If it's required and not present, throws a {@link com.kiwiko.webapp.mvc.requests.api.RequestError}.
     * If it's not required and not present, the deserialized value will be null.
     *
     * @return true if the value must be present in the request, or false if not
     * @throws com.kiwiko.webapp.mvc.requests.api.RequestError if required and not present
     */
    boolean required() default true;
}