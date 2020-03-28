package com.kiwiko.mvc.configuration;

import com.kiwiko.mvc.lifecycle.dependencies.api.DependencyRegistry;
import com.kiwiko.mvc.lifecycle.dependencies.data.DependencyBinding;
import com.kiwiko.mvc.lifecycle.dependencies.manual.data.InjectManuallyConfigurer;
import com.kiwiko.mvc.lifecycle.dependencies.manual.api.ManualDependencyRegistry;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.annotation.Annotation;

public class ConfigurationHelper {

    private final DependencyRegistry dependencyRegistry;

    public ConfigurationHelper(DependencyRegistry dependencyRegistry) {
        this.dependencyRegistry = dependencyRegistry;
    }

    public ConfigurationHelper() {
        this(new ManualDependencyRegistry());
    }

    /**
     * Source: https://stackoverflow.com/a/42883497
     */
    public <A extends Annotation> Advisor createAnnotationBean(Class<A> annotationType, Advice instance) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(String.format("@annotation(%s)", annotationType.getName()));
        return new DefaultPointcutAdvisor(pointcut, instance);
    }

    public <T> T createWithManualInjection(T instance) {
        InjectManuallyConfigurer<T> configurer = new InjectManuallyConfigurer<>();
        configurer.setInstance(instance);
        dependencyRegistry.all().stream()
                .forEach(configurer::addDependencyBinding);
        return configurer.create();
    }

    public <T> T createWithManualInjection(T instance, DependencyBinding dependencyBinding) {
        dependencyRegistry.register(dependencyBinding);
        return createWithManualInjection(instance);
    }
}
