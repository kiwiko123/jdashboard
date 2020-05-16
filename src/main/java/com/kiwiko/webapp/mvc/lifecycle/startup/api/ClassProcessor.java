package com.kiwiko.webapp.mvc.lifecycle.startup.api;

import com.kiwiko.library.metrics.api.LogService;
import com.kiwiko.webapp.mvc.lifecycle.dependencies.manual.api.annotations.InjectManually;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassProcessor {

    @InjectManually
    protected LogService logService;

    protected void processMethods(Collection<Method> methods) { }

    protected void processFields(Collection<Field> fields) { }

    protected void processClass(Class<?> clazz) { }

    public void process(Class<?> clazz) {
        logService.debug(String.format("[%s] Processing class %s", getClass().getSimpleName(), clazz.getName()));

        try {
            processClass(clazz);
        } catch (Exception e) {
            logService.error(String.format("Error processing class %s", clazz.getName()), e);
            handleException(e);
        }

        Collection<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toSet());
        try {
            processFields(fields);
        } catch (Exception e) {
            logService.error(String.format("Error processing fields for class %s", clazz.getName()), e);
            handleException(e);
        }

        Collection<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
                .collect(Collectors.toSet());
        try {
            processMethods(methods);
        } catch (Exception e) {
            logService.error(String.format("Error processing methods for class %s", clazz.getName()), e);
            handleException(e);
        }
    }

    protected void handleException(Exception exception) throws RuntimeException { }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        return getClass() == other.getClass();
    }

    @Override
    public int hashCode() {
        String key = getClass().getName();
        return Objects.hash(key);
    }
}
