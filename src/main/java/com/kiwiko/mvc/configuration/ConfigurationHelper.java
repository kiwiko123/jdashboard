package com.kiwiko.mvc.configuration;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.annotation.Annotation;

public class ConfigurationHelper {

    /**
     * Create an {@link Advisor} that can be registered like a {@link org.springframework.context.annotation.Bean}
     * in a Spring Boot {@link org.springframework.context.annotation.Configuration} class.
     * The {@link Advice} instance is the interceptor that powers the {@link Annotation}.
     * Source: <a href=https://stackoverflow.com/a/42883497>Intercepting annotated methods using Spring @Configuration and MethodInterceptor</a>.
     *
     * @param annotationType the {@link Annotation}'s class
     * @param instance an instance of the interceptor that powers the annotation
     * @return an Advisor that can be registered like a {@link org.springframework.context.annotation.Bean} in a {@link org.springframework.context.annotation.Configuration} class
     */
    public <A extends Annotation> Advisor createAnnotationBean(Class<A> annotationType, Advice instance) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(String.format("@annotation(%s)", annotationType.getName()));
        return new DefaultPointcutAdvisor(pointcut, instance);
    }
}
