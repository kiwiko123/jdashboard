package com.kiwiko.jdashboard.library.lang.types;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kiwiko.jdashboard.library.monitoring.logging.impl.console.ConsoleLogger;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;

import java.util.Map;

public class TypesHelper {

    private final Logger logger;

    private static final BiMap<Class<?>, Class<?>> objectTypesByPrimitiveType = HashBiMap.create(Map.of(
            boolean.class, Boolean.class,
            int.class, Integer.class,
            long.class, Long.class,
            double.class, Double.class,
            float.class, Float.class,
            byte.class, Byte.class,
            short.class, Short.class,
            char.class, Character.class));

    public TypesHelper() {
        logger = new ConsoleLogger();
    }

    /**
     * Given an arbitrary class, return the corresponding builtin type if one exists.
     * For example, builtin object types will return their corresponding primitive types, and vice versa.
     * If the type isn't builtin, or isn't recognized, the argument type will be returned.
     */
    public Class<?> getCorrespondingType(Class<?> type) {
        if (type.isPrimitive()) {
            if (!objectTypesByPrimitiveType.containsKey(type)) {
                logger.error(String.format("No mapped object type for primitive type %s", type.getName()));
            }
            return objectTypesByPrimitiveType.get(type);
        }

        return objectTypesByPrimitiveType.inverse().getOrDefault(type, type);
    }
}
