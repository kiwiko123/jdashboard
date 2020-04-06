package com.kiwiko.persistence.identification;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Provides an ID for an object.
 * {@link #getId()} provides safe access to the ID -- it's guaranteed to be non-null and cannot be overridden.
 *
 * @param <T> the type of the ID.
 */
public abstract class TypeIdentifiable<T> implements Identifiable<T> {

    private T id;

    /**
     * Default, preferred constructor for creating an object with an ID.
     *
     * @param id the non-null ID
     * @throws IllegalArgumentException if a null value is provided as the ID
     */
    public TypeIdentifiable(T id) throws IllegalArgumentException {
        setId(id);
    }

    /**
     * Sets the result of {@link #generateId()} as the object's ID.
     * Override {@link #generateId()} to allow derived instances to be instantiated without an explicit ID.
     * For example,
     * an implementation of {@link #generateId()} could return a monotonically increasing number on every invocation.
     *
     * @throws IllegalArgumentException if a null value is provided as the ID
     */
    protected TypeIdentifiable() {
        T calculatedId = generateId();
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
    protected T generateId() {
        return null;
    }

    protected void validateId(T id) throws RuntimeException { }

    private void setId(T id) {
        if (id == null) {
            throw new IllegalArgumentException(String.format("%s cannot have a null ID", getClass().getName()));
        }
        validateId(id);
        this.id = id;
    }
}
