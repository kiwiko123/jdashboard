package com.kiwiko.mvc.resolvers.startup;

import com.google.common.collect.Sets;
import com.kiwiko.mvc.lifecycle.startup.api.ClassProcessor;
import com.kiwiko.mvc.lifecycle.startup.api.errors.LifecycleException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public abstract class EndpointProcessor extends ClassProcessor {

    private static Collection<Class<?>> REQUEST_MAPPING_TYPES = Sets.newHashSet(RequestMapping.class, GetMapping.class, PostMapping.class);

    protected abstract void processMethod(Method method) throws RuntimeException;

    @Override
    protected void handleException(Exception exception) throws RuntimeException {
        throw new LifecycleException(exception);
    }

    @Override
    protected void processMethods(Collection<Method> methods) throws RuntimeException {
        methods.stream()
                .filter(this::isEndpointMethod)
                .forEach(this::processMethod);
    }

    private boolean isEndpointMethod(Method method) {
        return Arrays.stream(method.getDeclaredAnnotations())
                .map(Annotation::annotationType)
                .anyMatch(REQUEST_MAPPING_TYPES::contains);
    }
}
