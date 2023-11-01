package com.kiwiko.jdashboard.library.lang.enums;

import com.kiwiko.jdashboard.library.persistence.identification.Identifiable;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class EnumHelper {

    public static <IdType, E extends Enum & Identifiable<IdType>> Optional<E> getById(E[] enumValues, IdType id) {
        return Arrays.stream(enumValues)
                .filter(value -> Objects.equals(id, value.getId()))
                .findFirst();
    }
}
