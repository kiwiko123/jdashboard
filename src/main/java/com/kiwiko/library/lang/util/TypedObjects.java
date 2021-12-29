package com.kiwiko.library.lang.util;

import java.util.Objects;

public class TypedObjects {

    public static <T> boolean equals(T first, T second) {
        return Objects.equals(first, second);
    }

    public static <T> boolean notEquals(T first, T second) {
        return !equals(first, second);
    }
}
