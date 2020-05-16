package com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate fields with this to manually inject a dependency into a class at runtime.
 * This can be useful in situations when Spring hasn't yet processed its {@link org.springframework.context.annotation.Configuration}s,
 * like during application startup and inside of {@link org.aopalliance.intercept.MethodInterceptor}s.
 *
 * To support manual injection, the class containing manually-injected fields must be initialized via
 * {@link com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.data.InjectManuallyConfigurer#create()}.
 *
 * The annotated field's type must have a default constructor in order to be successfully initialized.
 *
 * Any error during manual injection will yield a {@link com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.api.errors.ManualInjectionException}.
 *
 * Currently, only field injection is supported.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectManually {
}
