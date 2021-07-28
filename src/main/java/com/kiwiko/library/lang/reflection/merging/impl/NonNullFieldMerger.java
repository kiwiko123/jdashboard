package com.kiwiko.library.lang.reflection.merging.impl;

import java.lang.reflect.Field;

public class NonNullFieldMerger<T> extends FieldMerger<T> {

    @Override
    protected boolean includeField(Field field, Object fieldValue) {
        return fieldValue != null;
    }
}
