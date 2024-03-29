package com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.annotations;

import com.kiwiko.jdashboard.webapp.framework.configuration.api.interfaces.JdashboardDependencyConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify the immediate configuration dependencies of a {@link org.springframework.context.annotation.Configuration}
 * class or a {@link org.springframework.context.annotation.Bean}. Transitive configuration classes do not need to be listed.
 *
 * Consider an example of car-buying software where we have {@code CarPurchaser.java} and {@code CreditCardVerifier.java}.
 * The former uses the latter in its business logic to validate the customer's credit card for a down payment.
 * <ol>
 *     <li>{@code CarConfiguration} wires {@code CarPurchaser}.</li>
 *     <li>{@code CreditCardConfiguration} wires {@code CreditCardVerifier}.</li>
 *     <li>{@code CarPurchaser} injects {@code CreditCardVerifier}.</li>
 * </ol>
 * {@literal
 *     // CreditCardConfiguration.java
 *     @Configuration
 *     public class CreditCardConfiguration {
 *          @Bean
 *          public CreditCardVerifier creditCardVerifier() {
 *              return new CreditCardVerifier();
 *          }
 *     }
 *
 *     // CarConfiguration.java
 *     @Configuration
 *     public class CarConfiguration {
 *          @Bean
 *          @ConfiguredBy(CreditCardConfiguration.class)
 *          public CarPurchaser carPurchaser() {
 *              return new CarPurchaser();
 *          }
 *     }
 *
 *     // CarPurchaser.java
 *     public class CarPurchaser {
 *          @Inject private CreditCardVerifier creditCardVerifier;
 *          // Class implementation...
 *     }
 * }
 * The {@code CarPurchaser} {@link org.springframework.context.annotation.Bean} is annotated with
 * {@literal @ConfiguredBy(CreditCardConfiguration.class)} because it relies on a dependency configured there.
 *
 * Currently, this annotation is non-functional -- it purely serves as documentation.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfiguredBy {

    Class<? extends JdashboardDependencyConfiguration>[] value();
}
