package com.kiwiko.library.persistence.identification;

import java.util.Objects;

public abstract class TypeIdentifiable<T> implements Identifiable<T> {

    @Override
    public String toString() {
        T id = getId();
        if (id == null) {
            return super.toString();
        }
        return String.format("%s(id=%s)", getClass().getSimpleName(), id.toString());
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

        Identifiable otherIdentifiable = (Identifiable) other;
        return Objects.equals(getId(), otherIdentifiable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
