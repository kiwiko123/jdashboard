package com.kiwiko.dataStructures;

import java.util.Objects;

public class Pair<T, K> {

    private final T first;
    private final K second;

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public K getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), first.toString(), second.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !getClass().isAssignableFrom(other.getClass())) {
            return false;
        }

        Pair otherPair = (Pair) other;
        return Objects.equals(first, otherPair.first) && Objects.equals(second, otherPair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
