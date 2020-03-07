package com.kiwiko.persistence.identification;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

/**
 * Provides an ID for an object.
 * The ID is guaranteed to be non-null.
 *
 * @param <T> the type of the ID.
 */
public abstract class TypeIdentifiable<T> implements Identifiable<T> {

    private T id;

    /**
     * Default, preferred constructor for creating an object with an ID.
     *
     * @param id the non-null ID.
     * @throws IllegalArgumentException if a null value is provided as the ID.
     */
    public TypeIdentifiable(T id) throws IllegalArgumentException {
        setId(id);
    }

    protected TypeIdentifiable() {
        T calculatedId = calculateId().orElse(null);
        setId(calculatedId);
    }

    /**
     * @return the non-null ID of the object
     */
    @Override
    @Nonnull
    public final T getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%s)", getClass().getSimpleName(), getId().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || !Identifiable.class.isAssignableFrom(other.getClass())) {
            return false;
        }

        Identifiable<T> otherIdentifiable = (Identifiable<T>) other;
        return Objects.equals(id, otherIdentifiable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Override this to return the ID that {@link #TypeIdentifiable()} should set.
     * Useful for requiring logic to pre-compute an ID in the constructor.
     *
     * @return the ID that {@link #TypeIdentifiable()} will set
     * @see GeneratedLongIdentifiable
     */
    protected Optional<T> calculateId() {
        return Optional.empty();
    }

    private void setId(T id) {
        if (id == null) {
            throw new IllegalArgumentException(String.format("%s cannot have a null ID", getClass().getName()));
        }
        this.id = id;
    }
}
