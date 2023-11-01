package com.kiwiko.jdashboard.library.files.properties.readers.api.dto;

import javax.annotation.Nullable;
import java.util.Objects;

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

    public MappableProperty<T> mappable() {
        Objects.requireNonNull(value, "Non-null value is required for a mappable property");
        return new MappableProperty<>(name, value);
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
