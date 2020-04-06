package com.kiwiko.lang.reflection.properties.internal;

import com.kiwiko.lang.reflection.ReflectionHelper;
import com.kiwiko.lang.reflection.properties.api.errors.PropertyMappingException;
import com.kiwiko.lang.types.TypesHelper;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class FieldMapperHelper {

    private final ReflectionHelper reflectionHelper;
    private final TypesHelper typesHelper;

    public FieldMapperHelper() {
        reflectionHelper = new ReflectionHelper();
        typesHelper = new TypesHelper();
    }

    /**
     * Given two classes, determine if they're equivalent.
     * The types are equivalent if either:
     *   - they're the same, or
     *   - one is the primitive version of the other
     *
     * For example, this returns true with the arguments (int.class, Integer.class).
     *
     * @param typeA
     * @param typeB
     * @return true if the types are equivalent, or false if not.
     */
    public boolean areEquivalentTypes(Class<?> typeA, Class<?> typeB) {
        if (typeA == typeB) {
            return true;
        }
        return Stream.of(typeA, typeB)
                .map(type -> type.isPrimitive() ? typesHelper.getCorrespondingType(type) : type)
                .distinct()
                .count() == 1l;
    }

    /**
     * Given a declared field and a target class, determine if the target class has a field of the same name and type.
     *
     * @param sourceField the source class' declared field.
     * @param targetType the target class.
     * @return true if the target class has a declared field of the same name and type, or false if not.
     */
    public boolean canCopyField(Field sourceField, Class<?> targetType) {
        return reflectionHelper.findField(targetType, sourceField.getName())
                .map(Field::getType)
                .map(fieldType -> areEquivalentTypes(fieldType, sourceField.getType()))
                .orElse(false);
    }

    public <S, T> void copyField(Field field, S source, T target) throws PropertyMappingException {
        Field targetField = reflectionHelper.findField(target.getClass(), field.getName())
                .orElseThrow(() -> new IllegalArgumentException(String.format("No such field \"%s\"", field.getName())));

        // Make the fields accessible.
        field.trySetAccessible();
        targetField.trySetAccessible();

        // Copy the value from the source field to the target field.
        try {
            targetField.set(target, field.get(source));
        } catch (IllegalAccessException e) {
            String message = String.format(
                    "Failed to copy field %s from type %s to type %s",
                    field.getName(),
                    source.getClass().getName(),
                    target.getClass().getName());
            throw new PropertyMappingException(message, e);
        }
    }
}
