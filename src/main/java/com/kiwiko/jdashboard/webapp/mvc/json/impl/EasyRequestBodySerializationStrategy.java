package com.kiwiko.jdashboard.webapp.mvc.json.impl;

import com.kiwiko.library.lang.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.webapp.mvc.json.api.CustomRequestBodySerializationStrategy;
import com.kiwiko.jdashboard.webapp.mvc.json.api.errors.JsonException;
import com.kiwiko.library.json.data.IntermediateJsonBody;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EasyRequestBodySerializationStrategy<T> implements CustomRequestBodySerializationStrategy<T> {

    protected final ReflectionHelper reflectionHelper;
    private Set<Field> failedFields;

    public EasyRequestBodySerializationStrategy() {
        reflectionHelper = new ReflectionHelper();
        failedFields = new HashSet<>();
    }

    @Override
    public T deserialize(IntermediateJsonBody body, T target) {
        Map<String, Object> bodyFields = body.asMap();
        reflectionHelper.getFields(target.getClass()).stream()
                .filter(field -> bodyFields.containsKey(field.getName()))
                .forEach(field -> attemptFieldCopy(body, field, target));

        return target;
    }

    protected Set<Field> getFailedFields() {
        return Collections.unmodifiableSet(failedFields);
    }

    private void attemptFieldCopy(IntermediateJsonBody body, Field targetField, T targetInstance) {
        Object value = body.getValue(targetField.getName()).orElse(null);
        targetField.trySetAccessible();

        try {
            targetField.set(targetInstance, value);
        } catch (IllegalAccessException e) {
            throw new JsonException(String.format("Failed to set field %s", targetField.getName()), e);
        } catch (Exception e) {
            failedFields.add(targetField);
        }
    }
}
