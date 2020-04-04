package com.kiwiko.lang.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ReflectionHelper {

    /**
     * Retrieves the fields of the current class and all parent classes.
     * Fields with the same name are prioritized bottom-up; for example, if the inheritance hierarchy is A > B,
     * where both A and B have private fields named "foo", then only B.foo will be included in the returned fields.
     *
     * @param type the class whose fields will be retrieved
     * @return all fields for the given class and its parent classes
     */
    public Collection<Field> getFields(Class<?> type) {
        Collection<Field> fields = new ArrayList<>();
        Set<String> visitedFieldNames = new HashSet<>();
        Class<?> nextType = type;

        while (canSearchUpwards(nextType)) {
            for (Field field : nextType.getDeclaredFields()) {
                if (!visitedFieldNames.contains(field.getName())) {
                    fields.add(field);
                    visitedFieldNames.add(field.getName());
                }
            }
            nextType = nextType.getSuperclass();
        }

        return fields;
    }

    /**
     * Given a class and a field name, search the class and all its parent classes for the given field.
     * The inheritance hierarchy is searched bottom-up, so the first match will be returned.
     *
     * @param type the class to search
     * @param fieldName the name of the field to search for
     * @return the first matching field
     */
    public Optional<Field> findField(Class<?> type, String fieldName) {
        Class<?> nextType = type;
        Optional<Field> result = Optional.empty();

        while (!result.isPresent() && canSearchUpwards(nextType)) {
            result = getDeclaredField(nextType, fieldName);
            nextType = nextType.getSuperclass();
        }

        return result;
    }

    /**
     * Finds the declared field for the given class, if any.
     * Only the given class is searched; no parent classes are searched.
     *
     * @param type the class to search
     * @param fieldName the name of the field to search for
     * @return the field of the given class, if any
     */
    public Optional<Field> getDeclaredField(Class<?> type, String fieldName) {
        try {
            return Optional.of(type.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            return Optional.empty();
        }
    }

    private boolean canSearchUpwards(Class<?> type) {
        return !(type == Object.class || type == null);
    }
}
