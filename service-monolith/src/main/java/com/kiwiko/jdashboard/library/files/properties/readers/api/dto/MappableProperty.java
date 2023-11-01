package com.kiwiko.jdashboard.library.files.properties.readers.api.dto;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MappableProperty<T> extends Property<T> {

    public MappableProperty(String name, @Nonnull T value) {
        super(name, value);
        Objects.requireNonNull(getValue(), "Non-null property value is required");
    }

    @Nonnull
    @Override
    public T getValue() {
        return super.getValue();
    }

    public String mapToString() {
        return mapTo(String.class, Objects::toString);
    }

    public int mapToInt() {
        return mapFromString(int.class, Integer::parseInt);
    }

    public long mapToLong() {
        return mapFromString(long.class, Long::parseLong);
    }

    public boolean mapToBool() {
        return mapFromString(boolean.class, Boolean::parseBoolean);
    }

    public <R> List<R> mapToList() {
        return mapToCollectionType(Collectors.toUnmodifiableList());
    }

    public <R> Set<R> mapToSet() {
        return mapToCollectionType(Collectors.toUnmodifiableSet());
    }

    private <R> R mapTo(Class<R> resultType, Function<T, R> mapper) {
        T value = getValue();
        if (resultType.isAssignableFrom(value.getClass())) {
            @SuppressWarnings("unchecked")
            R result = (R) value;
            return result;
        }

        return mapper.apply(value);
    }

    private <R> R mapFromString(Class<R> resultType, Function<String, R> mapper) {
        String value = mapToString();
        MappableProperty<String> stringProperty = new MappableProperty<>(getName(), value);
        return stringProperty.mapTo(resultType, mapper);
    }

    private <R, CollectionType extends Collection<R>> CollectionType mapToCollectionType(Collector<R, ?, CollectionType> collector) {
        String commaDelimited = mapToString();
        return Arrays.stream(commaDelimited.split(","))
                .map(String::trim)
                .map(value -> {
                    @SuppressWarnings("unchecked")
                    R casted = (R) value;
                    return casted;
                })
                .collect(collector);
    }
}
