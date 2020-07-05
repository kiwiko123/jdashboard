package com.kiwiko.library.lang.reflection;

import com.kiwiko.library.lang.reflection.properties.api.errors.PropertyMappingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    /**
     * Create a new, default instance of the given type T.
     * T must have a valid default constructor; otherwise this operation will fail.
     *
     * @param type the type from which a new instance will be created
     * @param <T> the type
     * @return a new, default instance of type T
     * @throws PropertyMappingException if creation fails
     */
    public <T> T createDefaultInstance(Class<T> type) throws PropertyMappingException {
        Constructor<T> constructor;

        // Attempt to get the default constructor.
        try {
            constructor = type.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            String message = String.format("No declared constructor found for type %s", type.getName());
            throw new PropertyMappingException(message, e);
        }

        // Attempt to make the default constructor accessible, in case it's non-public.
        constructor.trySetAccessible();
        T result;

        // Attempt to instantiate a new object.
        try {
            result = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            String message = String.format("Failed to create a new default instance of type %s", type.getName());
            throw new PropertyMappingException(message, e);
        }

        return result;
    }

    /**
     * Determine if the inheritance hierarchy of the given class can continue to search "upwards".
     * It will no longer search upwards if the type is {@link Object}, or null.
     *
     * @param type the type to check if it can search upwards
     * @return true if it can search upwards, or false if not
     */
    private boolean canSearchUpwards(Class<?> type) {
        return !(type == Object.class || type == null);
    }
}
