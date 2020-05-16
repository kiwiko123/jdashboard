package com.kiwiko.lang.types;

import com.kiwiko.metrics.api.LogService;
import com.kiwiko.metrics.impl.ConsoleLogService;

import java.util.Map;
import java.util.stream.Collectors;

public class TypesHelper {

    private final LogService logService;

    private static final Map<Class<?>, Class<?>> objectTypesByPrimitiveType = Map.of(
            boolean.class, Boolean.class,
            int.class, Integer.class,
            long.class, Long.class,
            double.class, Double.class,
            float.class, Float.class,
            byte.class, Byte.class,
            short.class, Short.class,
            char.class, Character.class);

    private static final Map<Class<?>, Class<?>> primitiveTypesByObjectType = objectTypesByPrimitiveType.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

    public TypesHelper() {
        logService = new ConsoleLogService();
    }

    /**
     * Given an arbitrary class, return the corresponding builtin type if one exists.
     * For example, builtin object types will return their corresponding primitive types, and vice versa.
     * If the type isn't builtin, or isn't recognized, the argument type will be returned.
     */
    public Class<?> getCorrespondingType(Class<?> type) {
        if (type.isPrimitive()) {
            if (!objectTypesByPrimitiveType.containsKey(type)) {
                logService.error(String.format("No mapped object type for primitive type %s", type.getName()));
            }
            return objectTypesByPrimitiveType.get(type);
        }

        return primitiveTypesByObjectType.containsKey(type)
                ? primitiveTypesByObjectType.get(type)
                : type;
    }
}
