package com.kiwiko.webapp.mvc.configuration.api.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Intended to annotate a {@link org.springframework.context.annotation.Configuration} class or
 * an {@link org.springframework.context.annotation.Bean} method in a configuration.
 *
 * If the bean requires other dependencies configured in other configurations, this annotation should list those other
 * configuration classes. Transitive configuration classes do not need to be listed.
 *
 * Consider an example of car-buying software where we have {@code CarPurchaser.java} and {@code CreditCardVerifier.java}.
 * The former uses the latter in its business logic to validate the customer's credit card for a down-payment.
 * <ol>
 *     <li>{@code CarPurchaser} is wired in {@code CarConfiguration}</li>
 *     <li>{@code CreditCardVerifier} is wired in {@code CreditCardConfiguration}</li>
 *     <li>{@code CarPurchaser} injects {@code CreditCardVerifier}</li>
 * </ol>
 * The {@link org.springframework.context.annotation.Bean} method in {@code CarConfiguration} that wires {@code CarPurchaser}
 * should be annotated with {@literal @ConfiguredBy(CreditCardConfiguration.class)} because it relies on a dependency configured there.
 *
 * Currently, this annotation is non-functional -- it purely serves as documentation.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfiguredBy {

    Class<?>[] value();
}
