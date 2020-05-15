package com.kiwiko.mvc.resolvers.startup;

import com.kiwiko.mvc.requests.api.annotations.RequestBodyParameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;

public class RequestBodyParameterMethodArgumentValidator extends EndpointProcessor {

    @Override
    protected void processMethod(Method method) throws IllegalArgumentException {
        Parameter invalidAnnotatedParameter = Arrays.stream(method.getParameters())
                .filter(this::shouldProcessParameter)
                .findFirst()
                .orElse(null);

        if (invalidAnnotatedParameter == null) {
            return;
        }

        String message = String.format(
                "@RequestBodyParameter doesn't support collections; use @RequestBodyCollectionParameter instead for %s#%s(%s)",
                method.getDeclaringClass().getName(),
                method.getName(),
                invalidAnnotatedParameter.getType().getSimpleName());
        throw new IllegalArgumentException(message);
    }

    private boolean shouldProcessParameter(Parameter parameter) {
        return parameter.getDeclaredAnnotation(RequestBodyParameter.class) != null
                && Collection.class.isAssignableFrom(parameter.getType());
    }
}
