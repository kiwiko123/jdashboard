package com.kiwiko.mvc.lifecycle.startup.api;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.internal.ConsoleLogService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassProcessor {

    protected final LogService logService;

    public ClassProcessor() {
        logService = new ConsoleLogService();
    }

    protected void processMethods(Collection<Method> methods) { }

    protected void processFields(Collection<Field> fields) { }

    protected void processClass(Class<?> clazz) { }

    public void process(Class<?> clazz) {
        processClass(clazz);

        Collection<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toSet());
        try {
            processFields(fields);
        } catch (Exception e) {
            logService.error(String.format("Error processing fields for class %s", clazz.getName()), e);
        }

        Collection<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
                .collect(Collectors.toSet());
        try {
            processMethods(methods);
        } catch (Exception e) {
            logService.error(String.format("Error processing methods for class %s", clazz.getName()), e);
        }
    }

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
