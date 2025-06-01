package com.kiwiko.jdashboard.library.lang.reflection.merging.impl;

import com.kiwiko.jdashboard.library.lang.reflection.ReflectionHelper;
import com.kiwiko.jdashboard.library.lang.reflection.merging.api.interfaces.ObjectMerger;
import com.kiwiko.jdashboard.library.lang.reflection.merging.api.interfaces.ObjectMergingException;

import java.lang.reflect.Field;

public class FieldMerger<T> implements ObjectMerger<T> {

    private final ReflectionHelper reflectionHelper;

    public FieldMerger() {
        reflectionHelper = new ReflectionHelper();
    }

    @Override
    public T merge(T input, T target) throws ObjectMergingException {
        for (Field field : reflectionHelper.getFields(target.getClass())) {
            field.trySetAccessible();
            Object inputValue;

            try {
                inputValue = field.get(input);
            } catch (IllegalAccessException e) {
                throw new ObjectMergingException(String.format("Unable to access input field %s", field), e);
            }

            if (!includeField(field, inputValue)) {
                continue;
            }

            try {
                field.set(target, inputValue);
            } catch (IllegalAccessException e) {
                throw new ObjectMergingException(String.format("Unable to set field on updatable object %s", field), e);
            }
        }

        return target;
    }

    protected boolean includeField(Field field, Object fieldValue) {
        return true;
    }
}
