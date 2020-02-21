package com.kiwiko.persistence;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Provides an ID for an object.
 * The ID is guaranteed to be non-null.
 *
 * @param <T> the type of the ID.
 */
public abstract class Identifiable<T> {

    private final @Nonnull T id;

    protected Identifiable(T id) {
        if (id == null) {
            throw new IllegalArgumentException(String.format("%s cannot have a null ID", getClass().getName()));
        }
        this.id = id;
    }

    @Nonnull
    public final T getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%s)", getClass().getName(), getId().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !(other instanceof Identifiable)) {
            return false;
        }

        Identifiable otherIdentifiable = (Identifiable) other;
        return Objects.equals(id, otherIdentifiable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
