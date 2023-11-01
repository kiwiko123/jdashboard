package com.kiwiko.jdashboard.library.lang.reflection.properties.api;

import com.kiwiko.jdashboard.library.lang.reflection.properties.api.errors.PropertyMappingException;
import com.kiwiko.jdashboard.library.lang.reflection.properties.internal.FieldMapperHelper;

/**
 * A class that uses reflection to copy field values from one object to another.
 * Fields with matching names and equivalent types are copied.
 *
 * @param <SourceType>
 * @param <TargetType>
 */
public abstract class FieldMapper<SourceType, TargetType> extends PartialPropertyMapper<SourceType, TargetType> {

    private final FieldMapperHelper fieldMapperHelper;

    protected FieldMapper() {
        super();
        fieldMapperHelper = new FieldMapperHelper();
    }

    @Override
    protected <T, V> void copyTo(T source, V destination) throws PropertyMappingException {
        fieldMapperHelper.copyFieldsToObject(source, destination);
    }
}
