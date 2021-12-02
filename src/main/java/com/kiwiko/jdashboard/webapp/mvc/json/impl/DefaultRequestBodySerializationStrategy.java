package com.kiwiko.jdashboard.webapp.mvc.json.impl;

import com.kiwiko.jdashboard.webapp.mvc.json.api.errors.JsonException;
import com.kiwiko.library.json.data.IntermediateJsonBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultRequestBodySerializationStrategy<T> extends EasyRequestBodySerializationStrategy<T> {

    @Override
    public T deserialize(IntermediateJsonBody body, T target) {
        T result = super.deserialize(body, target);
        Set<Field> failedFields = getFailedFields();

        if (!failedFields.isEmpty()) {
            List<String> failedFieldNames = failedFields.stream()
                    .map(Field::getName)
                    .collect(Collectors.toList());
            throw new JsonException(String.format("Failed to deserialize fields %s", failedFieldNames.toString()));
        }

        return result;
    }
}
