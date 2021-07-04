package com.kiwiko.library.files.properties.readers.api.dto;

import javax.annotation.Nullable;

public class Property<T> {
    private final String name;
    private final @Nullable T value;

    public Property(String name, @Nullable T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
