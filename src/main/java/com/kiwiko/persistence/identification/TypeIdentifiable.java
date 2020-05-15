package com.kiwiko.persistence.identification;

import java.util.Objects;

public abstract class TypeIdentifiable<T> implements Identifiable<T> {

    @Override
    public String toString() {
        if (getId() == null) {
            return super.toString();
        }
        return String.format("%s(id=%s)", getClass().getSimpleName(), getId().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        // If the other object is null, this can't be equal to it.
        if (other == null) {
            return false;
        }

        // Strict type check -- if they're not exactly the same type, they're not equal.
        if (getClass() != other.getClass()) {
            return false;
        }

        Identifiable<T> otherIdentifiable = (Identifiable<T>) other;
        return Objects.equals(getId(), otherIdentifiable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
