package com.kiwiko.jdashboard.langutils.types;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TypesHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypesHelper.class);

    private static final BiMap<Class<?>, Class<?>> objectTypesByPrimitiveType = HashBiMap.create(Map.of(
            boolean.class, Boolean.class,
            int.class, Integer.class,
            long.class, Long.class,
            double.class, Double.class,
            float.class, Float.class,
            byte.class, Byte.class,
            short.class, Short.class,
            char.class, Character.class));

    /**
     * Given an arbitrary class, return the corresponding builtin type if one exists.
     * For example, builtin object types will return their corresponding primitive types, and vice versa.
     * If the type isn't builtin, or isn't recognized, the argument type will be returned.
     */
    public Class<?> getCorrespondingType(Class<?> type) {
        if (type.isPrimitive()) {
            if (!objectTypesByPrimitiveType.containsKey(type)) {
                LOGGER.error(String.format("No mapped object type for primitive type %s", type.getName()));
            }
            return objectTypesByPrimitiveType.get(type);
        }

        return objectTypesByPrimitiveType.inverse().getOrDefault(type, type);
    }
}
