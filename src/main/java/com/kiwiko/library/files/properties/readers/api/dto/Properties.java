package com.kiwiko.library.files.properties.readers.api.dto;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Properties<T> {
    private final Map<String, Property<T>> properties;

    public Properties() {
        properties = new HashMap<>();
    }

    /**
     * @param propertyName the
     * @return the property associated with the given name, or null if no property is defined/associated
     */
    @Nullable
    public Property<T> getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    public void addProperty(Property<T> property) {
        properties.put(property.getName(), property);
    }

    public void removeProperty(String propertyName) {
        properties.remove(propertyName);
    }

    public Set<String> names() {
        return Collections.unmodifiableSet(properties.keySet());
    }
}
